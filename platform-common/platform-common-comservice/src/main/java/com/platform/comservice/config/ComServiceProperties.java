package com.ikinloop.platform.comservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-09-10 17:08
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "platform.comservice")
public class ComServiceProperties {
    private String appId;
    private String appSecret;
    private String codeUrl;
    private String accessUrl = "https://api.weixin.qq.com/cgi-bin/token";
}
