/*
 * 文件名：ReflectUtil.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：杨智
 * 修改时间：2018年2月5日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */

package com.platform.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReflectUtil {
    
    /**
     * 循环向上转型, 获取Field
     * 
     * @param object 子类对象
     * @return 字段
     * @see
     * @since 1.0
     */
    public static Field[] getDeclaredFields(Object object) {
        List<Field> fieldList = new ArrayList<>();
        Set<String> names = new HashSet<>();
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field[] fields = clazz.getDeclaredFields();
                for(Field field : fields) {
                    if(!names.contains(field.getName())) {
                        fieldList.add(field);
                        names.add(field.getName());
                    }
                }
            }
            catch (Exception e) {
            }
        }
        return fieldList.toArray(new Field[fieldList.size()]);
    }
}
