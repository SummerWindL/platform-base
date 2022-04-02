package com.platform.common.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Advance
 * @date 2022年01月17日 21:40
 * @since V1.0.0
 */
@EnableAdminServer // 添加此行代码
@SpringBootApplication
public class SbaserverApplication {
    public static void main(String[] args) {
        SpringApplication.run(SbaserverApplication.class, args);
    }
}
