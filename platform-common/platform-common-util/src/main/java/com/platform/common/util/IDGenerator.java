package com.platform.common.util;

import org.apache.commons.collections4.MapUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Advance
 * @date 2022年03月21日 14:33
 * @since V1.0.0
 */
public class IDGenerator implements IdentifierGenerator {
    /**
     * 获取一个32位长度的UUID
     * @return UUID
     */
    public static String generate(Object o) {
        return generate(o,32);
    }
    /**
     * 利用反射通过get方法获取bean中字段fieldName的值
     * @param bean {@link Object}
     * @param fieldName {@link String}
     * @return {@link Object}
     * @throws Exception Exception
     */
    private static Object getFieldValue(Object bean, String fieldName) throws Exception {
        String methodName = "get" + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);

        Object rObject;
        Method method;

        @SuppressWarnings("rawtypes")
        Class[] classArr = new Class[0];
        method = bean.getClass().getMethod(methodName, classArr);
        rObject = method.invoke(bean);

        return rObject;
    }

    /**
     * 获取一个指定长度的UUID<br>
     * length可取值为：12、16、20、32，如果不符合以上4个值，则直接返回36位UUID
     * @param length 长度
     * @return UUID
     */
    public static String generate(Object o,int length) {
        Map<String,Object> fieldsMap = new HashMap<>();
        Field[] declaredFields = o.getClass().getDeclaredFields();
        for (Field f : declaredFields) {
            if ("java.lang.String".equals(f.getType().getName())) {
                //获取字段名
                String key = f.getName();
                Object value = null;
                try {
                    value = getFieldValue(o, key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (value == null) {
                    continue;
                }
                fieldsMap.put(key,value);
            }
        }
        if(ObjectUtils.isEmpty(fieldsMap.get("id"))){
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
        return fieldsMap.get("id").toString();
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return generate(o);
    }
}

