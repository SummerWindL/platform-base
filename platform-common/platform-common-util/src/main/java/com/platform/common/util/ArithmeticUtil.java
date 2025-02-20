/*
 * 文件名：ArithmeticUtil.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：杨智
 * 修改时间：2018年1月30日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */

package com.platform.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ArithmeticUtil {

    /**
     * 提供精确的加法运算，如果某个参数为空，则不作为加算数据
     * 
     * @param datas 数据（数值）
     * @return 加算结果（默认为0）
     */
    public static BigDecimal doAdd(int scale, RoundingMode roundingMode, BigDecimal... datas) {
        BigDecimal result = BigDecimal.ZERO;
        for (BigDecimal data : datas) {
            if (null != data) {
                result = result.add(data);
            }
        }
        return round(result, scale, roundingMode);
    }


    /**
     * 提供精确的加法运算，如果某个参数为空，则不作为加算数据
     *
     * @param datas 数据（数值）
     * @return 加算结果（默认为0）
     */
    public static BigDecimal doAdd(BigDecimal... datas) {
        BigDecimal result = BigDecimal.ZERO;
        for (BigDecimal data : datas) {
            if (null != data) {
                result = result.add(data);
            }
        }
        return result;
    }

    /**
     * 提供精确的减法运算，参数为空的作为0处理
     * 
     * @param data1 被减数（数值）
     * @param data2 减数（数值）
     * @return 加算结果（默认为0）
     */
    public static BigDecimal subtract(BigDecimal data1, BigDecimal data2) {
        BigDecimal result = BigDecimal.ZERO;
        if (null != data1) {
            result = result.add(data1);
        }
        if (null != data2) {
            result = result.subtract(data2);
        }
        return result;
    }

    /**
     * 提供精确的减法运算，参数为空的作为0处理
     * 
     * @param data1 被减数（数值）
     * @param data2 减数（数值）
     * @return 加算结果（默认为0）
     */
    public static BigDecimal subtract(BigDecimal data1, BigDecimal data2, int scale,
                                      RoundingMode roundingMode) {
        return round(subtract(data1, data2), scale, roundingMode);
    }

    /**
     * 提供精确乘法运算方法
     *
     * @param datas 乘数
     * @return 两个参数的积
     */
    public static BigDecimal multiply(BigDecimal... datas) {
        BigDecimal result = BigDecimal.ONE;
        for (BigDecimal data : datas) {
            if (data == null) {
                return null;
            }
            result = result.multiply(data);
        }
        return result;
    }

    /**
     * 提供精确的除法运算方法
     * 
     * @param value1 被除数
     * @param value2 除数
     * @param scale 精确范围
     * @return 两个参数的商
     */
    public static BigDecimal divide(BigDecimal value1, BigDecimal value2, int scale) {
        return divide(value1, value2, scale, RoundingMode.HALF_UP);
    }

    /**
     * 提供精确的除法运算方法
     * 
     * @param value1 被除数
     * @param value2 除数
     * @param scale 精确范围
     * @return 两个参数的商
     */
    public static BigDecimal divide(BigDecimal value1, BigDecimal value2, int scale,
                                    RoundingMode roundingMode) {
        if (value1 == null || value2 == null || value2.doubleValue() == 0) {
            return BigDecimal.ZERO;
        }
        // 如果精确范围小于0，抛出异常信息
        if (scale < 0) {
            scale = 2;
        }
        return value1.divide(value2, scale, roundingMode);
    }

    /**
     * 提供精确的除法运算方法
     * 
     * @param value1 被除数
     * @param value2 除数
     * @param scale 精确范围
     * @return 两个参数的商
     */
    public static BigDecimal divide(BigDecimal value1, int value2, int scale) {
        if (value1 == null) {
            return value1;
        }
        // 如果精确范围小于0，默认为12
        if (scale < 0) {
            scale = 12;
        }
        return divide(value1, new BigDecimal(value2), scale, RoundingMode.HALF_UP);
    }

    /**
     * 调整值精度
     * 
     * @param value 值
     * @param scale 精度
     * @param roundingMode 无设置默认四舍五入类型
     * @return double
     */
    public static BigDecimal round(BigDecimal value, int scale, RoundingMode roundingMode) {
        if (value == null) {
            return null;
        }
        if (roundingMode == null) {
            roundingMode = RoundingMode.HALF_UP;
        }
        return value.setScale(scale, roundingMode);
    }
}
