package com.platform.core.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Advance
 * @date 2022年07月17日 12:08
 * @since V1.0.0
 */
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