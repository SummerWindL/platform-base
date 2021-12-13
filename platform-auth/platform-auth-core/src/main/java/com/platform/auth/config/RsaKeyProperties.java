package com.platform.auth.config;

import com.platform.auth.util.RsaUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author Advance
 * @date 2021年12月10日 14:41
 * @since V1.0.0
 * 获取公钥私钥配置类
 */
@Data
@ConfigurationProperties("rsa.key")   //配置指定key
public class RsaKeyProperties {
    private String pubKeyPath;

    private String priKeyPath;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @PostConstruct
    public void createKey() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

}
