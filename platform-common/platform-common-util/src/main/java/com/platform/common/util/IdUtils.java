package com.platform.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: platform-base
 * @description: id生成器
 * @author: fuyl
 * @create: 2019-07-30 11:54
 **/

public class IdUtils {
    private static int sequence = 0;

    /***
     * @Description: YYYYMMDDHHMMSS+6位自增长码(20位)
     * @Param: []
     * @return: java.lang.String
     * @Author: fuyl
     * @Date: 2019/7/30
     */
    public static synchronized String getLocalTrmSeqNum(String id,int length) {
        sequence = sequence >= 999999 ? 1 : sequence + 1;
        String datetime = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        String s = Integer.toString(sequence);
        return id.isEmpty()?datetime +addLeftZero(s, length):id;
    }
    /***
     * @Description: 左填0
     * @Param: [s, length]
     * @return: java.lang.String
     * @Author: fuyl
     * @Date: 2019/7/30
     */
    public static String addLeftZero(String s, int length) {
        // StringBuilder sb=new StringBuilder();
        int old = s.length();
        if (length > old) {
            char[] c = new char[length];
            char[] x = s.toCharArray();
            if (x.length > length) {
                throw new IllegalArgumentException(
                        "Numeric value is larger than intended length: " + s
                                + " LEN " + length);
            }
            int lim = c.length - x.length;
            for (int i = 0; i < lim; i++) {
                c[i] = '0';
            }
            System.arraycopy(x, 0, c, lim, x.length);
            return new String(c);
        }
        return s.substring(0, length);

    }
}
