package com.platform.common.util;

import org.springframework.util.Base64Utils;

/**
 * @program: platform-base
 * @description: base64加密
 * @author: fuyl
 * @create: 2019-08-22 16:42
 **/

public class Base64Util {
    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(byte[] key){
        return Base64Utils.decode(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key){
        return new String(Base64Utils.encode(key));
    }
}
