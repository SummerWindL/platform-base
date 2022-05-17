package com.platform.comservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-09-10 17:03
 **/
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.platform.comservice.config"})
public class PlatformComServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformComServiceApplication.class);
    }
}
