package com.platform.auth.config;

import com.platform.auth.util.RsaUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PublicKey;

/**
 * @author Advance
 * @date 2020/8/9 23:49
 */
//@Data
//@ConfigurationProperties("rsa.key")     //指定配置文件的key
/*public class RsaKeyProperties {

    private String pubKeyPath;

    private PublicKey publicKey;

    @PostConstruct
    public void createKey() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
    }
}*/
