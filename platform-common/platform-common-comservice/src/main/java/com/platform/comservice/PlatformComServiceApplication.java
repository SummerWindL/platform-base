package com.platform.comservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-09-10 17:03
 **/
@SpringBootApplication(scanBasePackages = {"com.platform", "com.lemon", "com.platform.common"})
public class PlatformComServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformComServiceApplication.class);
    }
}
