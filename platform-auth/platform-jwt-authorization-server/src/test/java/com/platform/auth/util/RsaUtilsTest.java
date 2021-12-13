package com.platform.auth.util;

import org.junit.Test;

/**
 * @author Advance
 * @date 2021年12月10日 15:12
 * @since V1.0.0
 */
public class RsaUtilsTest {
    private String privateFilePath = "D:\\rsa\\id_key_rsa";
    private String publicFilePath = "D:\\rsa\\id_key_rsa.pub";

    @Test
    public void generateKey() throws Exception {
        RsaUtils.generateKey(publicFilePath,privateFilePath,"Aix_Platform",2048);
    }

    @Test
    public void getPublicKey() throws Exception {
        System.out.println(RsaUtils.getPublicKey(publicFilePath));
    }

    @Test
    public void getPrivateKey() throws Exception {
        System.out.println(RsaUtils.getPrivateKey(privateFilePath));
    }
}
