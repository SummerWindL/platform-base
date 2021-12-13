package com.platform.auth;

import com.platform.auth.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author Advance
 * @date 2021年12月10日 16:58
 * @since V1.0.0
 */
@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)  //将配置类放入Spring容器中
public class AuthCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthCoreApplication.class);
    }
}
