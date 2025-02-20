package com.platform.core.util;

/**
 * @author Advance
 * @date 2022年07月17日 11:50
 * @since V1.0.0
 */
public class SysCodeUtil {
    private static String SYSTEM_CODE = "ALL";

    public static void cacheSysCode(String systemCode) {
        SYSTEM_CODE = systemCode;
    }

    public static String getSysCode() {
        return SYSTEM_CODE;
    }
}
