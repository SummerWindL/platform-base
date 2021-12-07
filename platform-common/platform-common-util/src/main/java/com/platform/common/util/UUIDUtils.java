/**
 * 2015年10月13日 下午5:57:32
 */
package com.platform.common.util;

import java.util.UUID;

/**
 * @author Jesse Zheng
 */
public class UUIDUtils {
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getUUID(String id) {
        if (id.isEmpty()) {
            return UUID.randomUUID().toString().replaceAll("-", "");
        } else {
            return id;
        }

    }
}
