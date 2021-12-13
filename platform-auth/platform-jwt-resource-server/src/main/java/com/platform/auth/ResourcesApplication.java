package com.platform.auth;

import com.platform.auth.config.RsaKeyProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 资源服务器启动类
 * @author Advance
 * @date 2021年12月13日 11:33
 * @since V1.0.0
 */
@SpringBootApplication
@MapperScan("com.platform.auth.mapper")
@EnableConfigurationProperties(RsaKeyProperties.class)  //将配置类放入Spring容器中
public class ResourcesApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResourcesApplication.class,args);
    }
}
