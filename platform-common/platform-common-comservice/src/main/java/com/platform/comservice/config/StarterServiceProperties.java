package com.platform.comservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Advance
 * @date 2022年04月12日 10:28
 * @since V1.0.0
 */
@ConfigurationProperties(prefix = "platform.aix.door")
public class StarterServiceProperties {

    private String userStr;

    public String getUserStr() {
        return userStr;
    }

    public void setUserStr(String userStr) {
        this.userStr = userStr;
    }
}
