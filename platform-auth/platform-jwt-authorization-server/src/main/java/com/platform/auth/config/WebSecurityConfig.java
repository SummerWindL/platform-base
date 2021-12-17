package com.platform.auth.config;

import com.platform.auth.filter.JwtLoginFilter;
import com.platform.auth.handler.DefaultAccessDeniedHandle;
import com.platform.auth.handler.DefaultAuthenticationEntryPoint;
import com.platform.auth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



/**
 * @author Robod
 * @date 2020/8/9 15:47
 */
@Configuration
@EnableWebSecurity      //加了这个注解才能写SpringSecurity相关的配置
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    private final RsaKeyProperties rsaKeyProperties;

    private final AuthCoreConfig authCoreConfig;
    //用户无权限自定义返回
    private DefaultAccessDeniedHandle defaultAccessDeniedHandle;
    //用户未登录或token失效自定义返回
    private DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint;

    @Autowired
    public void setDefaultAccessDeniedHandle(DefaultAccessDeniedHandle defaultAccessDeniedHandle) {
        this.defaultAccessDeniedHandle = defaultAccessDeniedHandle;
    }
    @Autowired
    public void setDefaultAuthenticationEntryPoint(DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint) {
        this.defaultAuthenticationEntryPoint = defaultAuthenticationEntryPoint;
    }

    public WebSecurityConfig(UserService userService, RsaKeyProperties rsaKeyProperties, AuthCoreConfig authCoreConfig) {
        this.userService = userService;
        this.rsaKeyProperties = rsaKeyProperties;
        this.authCoreConfig = authCoreConfig;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证用户的来源
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //数据库中
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    /**
     * 配置SpringSecurity相关信息
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()  //关闭csrf
                .addFilter(new JwtLoginFilter(super.authenticationManager(),rsaKeyProperties,authCoreConfig))
                .exceptionHandling()
                .accessDeniedHandler(defaultAccessDeniedHandle) // 自定义无权限访问
                .authenticationEntryPoint(defaultAuthenticationEntryPoint) // 自定义未登录返回
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);    //禁用session
    }

}
