package com.platform.auth.distributionkeys;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.authorization.*;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 认证服务器配置
 *
 * @author yanl.fu 20210905
 */
public class AuthorizationConfig {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationConfig.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ================================================

    /**
     * 个性化 JWT token
     */
    class CustomOAuth2TokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

        @Override
        public void customize(JwtEncodingContext context) {
            // 添加一个自定义头
            context.getHeaders().header("client-id", context.getRegisteredClient().getClientId());
        }
    }
    // ================================================

    /**
     * 定义 Spring Security 的拦截器链，比如我们的 授权url、获取token的url 需要由那个过滤器来处理，此处配置这个。
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        // ================================================
        // 设置jwt token个性化
        http.setSharedObject(OAuth2TokenCustomizer.class, new CustomOAuth2TokenCustomizer());
        // ================================================

        // 授权服务器配置
        OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer<>();

        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        return http
                .requestMatcher(endpointsMatcher)
                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .apply(authorizationServerConfigurer)
                .and()
                .formLogin()
                .and()
                .build();
    }

    /**
     * 创建客户端信息，可以保存在内存和数据库，此处保存在数据库中
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                // 客户端id 需要唯一
                .clientId("csdn")
                // 客户端密码
                .clientSecret(passwordEncoder.encode("csdn123"))
                // 可以基于 basic 的方式和授权服务器进行认证
                .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
                // 授权码
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                // 刷新token
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                // 客户端模式
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                // 密码模式
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                // 简化模式，已过时，不推荐
                .authorizationGrantType(AuthorizationGrantType.IMPLICIT)
                // 重定向url
                .redirectUri("https://www.baidu.com")
                // 客户端申请的作用域，也可以理解这个客户端申请访问用户的哪些信息，比如：获取用户信息，获取用户照片等
                .scope("user.userInfo")
                .scope("user.photos")
                .build();

        JdbcRegisteredClientRepository jdbcRegisteredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);
        if (null == jdbcRegisteredClientRepository.findByClientId("csdn")) {
            jdbcRegisteredClientRepository.save(registeredClient);
        }

        return jdbcRegisteredClientRepository;
    }

    /**
     * 保存授权信息，授权服务器给我们颁发来token，那我们肯定需要保存吧，由这个服务来保存
     */
    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
        JdbcOAuth2AuthorizationService authorizationService = new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);

        class CustomOAuth2AuthorizationRowMapper extends JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper {
            public CustomOAuth2AuthorizationRowMapper(RegisteredClientRepository registeredClientRepository) {
                super(registeredClientRepository);
                getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                this.setLobHandler(new DefaultLobHandler());
            }
        }

        CustomOAuth2AuthorizationRowMapper oAuth2AuthorizationRowMapper =
                new CustomOAuth2AuthorizationRowMapper(registeredClientRepository);

        authorizationService.setAuthorizationRowMapper(oAuth2AuthorizationRowMapper);
        return authorizationService;
    }

    /**
     * 如果是授权码的流程，可能客户端申请了多个权限，比如：获取用户信息，修改用户信息，此Service处理的是用户给这个客户端哪些权限，比如只给获取用户信息的权限
     */
    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
    }

    /**
     * 对JWT进行签名的 加解密密钥
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID("csdn")
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        // ================================================
        return (jwkSelector, securityContext) -> {
            Set<String> keyIds = jwkSelector.getMatcher().getKeyIDs();
            for (String keyId : keyIds) {
                if (jwkSet.getKeyByKeyId(keyId) != null) {
                    JWK jwk = jwkSet.getKeyByKeyId(keyId);
                    List<JWK> result = new ArrayList<>(1);
                    result.add(jwk);
                    return result;
                }
            }
            return null;
        };
        // ================================================
    }

    // ================================================
    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new CustomJwtEncoder(jwkSource);
    }
    // ================================================

    /**
     * jwt 解码
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     * 配置一些断点的路径，比如：获取token、授权端点 等
     */
    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder()
                // 发布者的url地址,一般是本系统访问的根路径
                // 此处的 qq.com 需要修改我们系统的 host 文件
                .issuer("http://qq.com:8080")
                .build();
    }
}
