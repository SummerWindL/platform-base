package com.platform.es;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 */
@SpringBootApplication
@MapperScan("com.platform.es.demo.mapper")
public class PlatformEsApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlatformEsApplication.class);
    }
}
