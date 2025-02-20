package com.platform.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;


/**
 * @author Advance
 * @date 2022年07月17日 12:05
 * @since V1.0.0
 */
public class BeanUtil extends BeanUtils {

    /**
     *
     */
    private static Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    /**
     * 把map转化为java bean
     *
     * @param <T> 返回类型
     * @param map Map对象
     * @param beanClass bean申明
     * @return 对象
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    public static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass) {
        if (map == null) {
            return null;
        }
        try {
            Object obj = beanClass.newInstance();
            populate(obj, map);
            return (T)obj;
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            try {
                return (T)map2Object(map, beanClass);
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 采用反射将map转为object
     *
     * @param map 参数
     * @param beanClass 目标类
     * @return Object
     * @throws InstantiationException 异常
     * @throws IllegalAccessException 异常
     */
    private static Object map2Object(Map<String, Object> map, Class<?> beanClass)
            throws InstantiationException, IllegalAccessException {
        if (map == null) {
            return null;
        }
        Object obj = beanClass.newInstance();
        Field[] fields = ReflectUtil.getDeclaredFields(obj);
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            try {
                Object value = map.get(field.getName());
                if(null != value) {
                    if(field.getType().equals(Date.class)){
                        field.set(obj,DateUtil.parse(value.toString()));
                    }
                    else if(field.getType().equals(String.class)){
                        field.set(obj,value.toString());
                    }
                    else{
                        field.set(obj,JsonUtils.json2Object(value.toString(), field.getType()) );
                    }
                }
                else {
                    logger.debug("{}中的{}为空", beanClass.getName(), field.getName());
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

        return obj;
    }

    /**
     * java bean 转 map
     *
     * @param obj 对象
     * @return Map
     * @since 1.0
     */
    public static Map<?, ?> objectToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        return new BeanMap(obj);
    }

    /**
     * java bean 转 map
     *
     * @param obj 对象
     * @return Map
     * @since 1.0
     */
    public static Map<String, Object> object2Map(Object obj) {
        Map<String, Object> result = new HashMap<>();
        if (obj == null) {
            return null;
        }
        BeanMap beanMap = new BeanMap(obj);
        beanMap.forEach((key, value) -> {
            if (!StringUtil.equals("class", key.toString())) {
                result.put(key.toString(), value);
            }
        });
        return result;
    }

    /**
     * Copy the property values of the given source bean into the given target bean, ignoring the
     * null value properties.
     *
     * @param dest 目标对象
     * @param orig 原对象
     * @since 1.0
     */
    public static void copyPropertiesIgnoreNull(Object dest, Object orig) {
        org.springframework.beans.BeanUtils.copyProperties(orig, dest, getNullPropertyNames(orig));
    }

    /**
     * 获取对象的所有null的属性
     *
     * @param source 对象
     * @return 对象的所有null的属性
     * @see
     * @since 1.0
     */
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            // 删除工作流参数
            if (StringUtil.equals(pd.getName(), "workflowParameter")) {
                continue;
            }
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
