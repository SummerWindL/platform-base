/**
 * 2015年10月13日 下午5:57:32
 */
package com.platform.common.util;

import java.security.MessageDigest;

/**
 *
 */
public class MD5Util {

    public static String md5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            return toHex(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String toHex(byte[] bytes) {

        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }

    public static void main(String[] args) {
        String aaa = "ffsdfwrgfevefve rg erfr4f5s4f5s4f5sd4fsdf w sfsd5f45d fs frwe s s";
        System.out.println(MD5Util.md5(aaa));
    }
}
