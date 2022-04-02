package com.platform.core.util;

import java.util.UUID;

/**
 * @author Advance
 * @date 2022年03月21日 14:33
 * @since V1.0.0
 */
public class IDGenerator {
    /**
     * 获取一个32位长度的UUID
     * @return UUID
     */
    public static String generate() {
        return generate(32);
    }

    /**
     * 获取一个指定长度的UUID<br>
     * length可取值为：12、16、20、32，如果不符合以上4个值，则直接返回36位UUID
     * @param length 长度
     * @return UUID
     */
    public static String generate(int length) {
        String uuid = UUID.randomUUID().toString();
        String[] components = uuid.split("-");
        if(components.length == 5) {
            if(length == 20) {
                return components[0] + components[1] + components[2] + components[3];
            }
            else if(length == 32) {
                return components[0] + components[1] + components[2] + components[3] + components[4];
            }
        }
        return uuid;
    }
}
