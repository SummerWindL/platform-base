package com.platform.auth;

import com.platform.auth.config.RsaKeyProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author Advance
 * @date 2021年12月10日 11:49
 * @since V1.0.0
 */
@SpringBootApplication
@MapperScan("com.platform.auth.mapper")
@EnableConfigurationProperties(RsaKeyProperties.class)  //将配置类放入Spring容器中
public class AuthorizationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationApplication.class, args);
    }
}
