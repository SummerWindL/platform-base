package com.platform.quartza;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author Advance
 * @date 2021年12月03日 16:13
 * @since V1.0.0
 */
@EnableScheduling
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@MapperScan("com.platform.quartza.mapper")
public class PlatformQuartzaApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlatformQuartzaApplication.class);
    }
}
