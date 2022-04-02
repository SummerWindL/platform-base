package com.platform.auth.bak.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Advance
 * @date 2021年12月09日 16:20
 * @since V1.0.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "platform.aix.jwtconfig")
public class JwtConfig {
    /**
     * token过期时间
     */
    private Long accessTokenExpire;
    /**
     * token刷新过期时间
     */
    private Long refreshTokenExpire;
    /**
     * token秘钥
     */
    private String accessTokenSecret;

    /**
     * token刷新秘钥
     */
    private String refreshTokenSecret;
}
