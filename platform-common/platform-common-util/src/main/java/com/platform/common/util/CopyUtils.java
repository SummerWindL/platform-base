package com.platform.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.platform.common.exception.BeanCopyException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 属性拷贝,可以只拷贝部分数据
 * 判断对象属性为空时,是否需要拷贝。如更新心电数据，可以只更新部分数据
 *
 * @author Michael
 * @version 2.0
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CopyUtils {

    private static final Log log = LogFactory.getLog(CopyUtils.class);

    /**
     * 基本类型、包装类型、String类型
     */
    public static final List<String> types = Arrays.asList("java.lang.Integer", "java.lang.Double", "java.lang.Float",
            "java.lang.Long", "java.lang.Short", "java.lang.Byte", "java.lang.Boolean", "java.lang.Character",
            "java.lang.String", "int", "double", "long", "short", "byte", "boolean", "char", "float");

    /**
     * 属性拷贝时定义时间格式类型的key
     */
    public static final String COPY_DATE_FORMAT = "copyDateFormat";

    /**
     * 日期格式的字段
     * 更新受检人信息时有date、datetime格式的，以此区分
     */
    private static final List<String> dateFormatLists = Arrays.asList("birth");

    /**
     * 拷贝属性,当source类属性是基础类型时的拷贝
     *
     * @param source
     * @param clazz
     */
    public static void copyProperties(Object source, Object target, Map<String, Object> copyParamsMap)
            throws BeanCopyException {
        try {
            // 原类中申明的属性信息，包括父类中声明的属性
            List<Field> lst = new ArrayList<Field>();
            getClassFields(source.getClass(), lst);
            // Field[] sourceDeclaredFields =
            // source.getClass().getDeclaredFields();
            for (Field sourceField : lst) {
                // 原类属性的返回类型
                String sourceReturnType = sourceField.getType().getName();
                // 设置访问权限
                sourceField.setAccessible(true);
                // 获取当前属性的值
                Object value = sourceField.get(source);

                // 当原类的类型是基础类型
                if (types.contains(sourceReturnType)) {
                    // copy属性
                    copyProperty(source, target, sourceField, sourceReturnType, value, copyParamsMap);
                }
            }
        } catch (Exception e) {
            String errorMsg = "Copy Properties from " + source + " to " + target + " Failed! Exceptipon: " + e.getMessage();
            log.error(errorMsg, e);
            throw new BeanCopyException(errorMsg, e);
        }
    }

    /**
     * 拷贝属性,当source类中属性有空时,根据标志位来确定是否需要复制
     *
     * @param source
     * @param clazz
     * @param copyParamsMap 支持多个参数,在转化时需要
     */
    public static void copyProperties(Object source, Object target, boolean sourceNullCopy,
                                      Map<String, Object> copyParamsMap) throws BeanCopyException {
        try {
            // 原类中申明的属性信息，包括父类中声明的属性
            List<Field> lst = new ArrayList<Field>();
            getClassFields(source.getClass(), lst);
            for (Field sourceField : lst) {
                // 原类属性的返回类型
                String sourceReturnType = sourceField.getType().getName();
                // 设置访问权限
                sourceField.setAccessible(true);
                // 获取当前属性的值
                Object value = sourceField.get(source);

                // 当原类的类型不是基础类型，且值为空，并且不需要拷贝Null属性时，直接循环
//				if (!types.contains(sourceReturnType) && null == value && !sourceNullCopy) {
//					continue;
//				}
                // 上面为何一定要加基础类型的限定？
                if (null == value && !sourceNullCopy) {
                    continue;
                }
                // copy属性
                copyProperty(source, target, sourceField, sourceReturnType, value, copyParamsMap);
            }
        } catch (BeanCopyException e) {
            throw e;
        } catch (Exception e) {
            String errorMsg = "Copy Properties from " + source + " to " + target + " Failed! Exceptipon: " + e.getMessage();
            log.error(errorMsg, e);
            throw new BeanCopyException(errorMsg, e);
        }
    }

    // 取得类的所有属性
    @SuppressWarnings("rawtypes")
    private static void getClassFields(Class clz, List<Field> lst) {
        Field[] fields = clz.getDeclaredFields();
        for (Field f : fields) {
            lst.add(f);
        }
        clz = clz.getSuperclass();
        if (clz != null) {
            getClassFields(clz, lst);
        }
    }

    /**
     * 处理属性拷贝方法,包括类型转化
     *
     * @param source           原类
     * @param target           目标类
     * @param sourceField      目标属性
     * @param sourceReturnType 目标属性对应的返回值类型
     * @param value            原属性的值
     * @param copyParamsMap    支持多个参数,在转化时需要
     * @throws Exception
     */
    private static void copyProperty(Object source, Object target, Field sourceField, String sourceReturnType,
                                     Object value, Map<String, Object> copyParamsMap) throws Exception {
        Field targetField = getTargetField(target.getClass(), sourceField.getName());
        // 目标类指定属性名的Field对象
        if (targetField == null) {
            return;
        }

        // 目标类的属性返回值类型
        String targetReturnType = targetField.getType().getName();

        sourceField.setAccessible(true);
        // 若原属性与目标属性类型一致，则直接获取原属性的值进行设置；若不一致，则需要进行转化
        if (!sourceReturnType.equals(targetReturnType)) {
            if (null != value) {
                try {
                    value = handleCorrectValueInfo(sourceReturnType, targetReturnType, copyParamsMap, targetField, value);
                } catch (Exception e) {
                    String errorMsg = "Copy Properties convert correct value from " + source + " to " + target + " Failed ! Exceptipon: " + e.getMessage();
                    log.error(errorMsg, e);
                    throw new BeanCopyException(errorMsg, e);
                }
            }
        }
        // 根据属性进行属性值的拷贝
        BeanUtils.copyProperty(target, sourceField.getName(), value);
    }

    private static Field getTargetField(Class<?> targetClass, String fieldName) {
        Field field = null;
        try {
            field = targetClass.getDeclaredField(fieldName);
            return field;
        } catch (NoSuchFieldException e) {
            Class<?> parentClass = targetClass.getSuperclass();
            if (parentClass != null) {
                return getTargetField(parentClass, fieldName);
            }
        }
        return null;
    }

    /**
     * 当原类型与目标类型不一致时，处理正确的value值
     */
    private static Object handleCorrectValueInfo(String sourceReturnType, String targetReturnType,
                                                 Map<String, Object> copyParamsMap, Field targetField, Object value) throws Exception {
        // 若目标对象属性为Date，获取copyParamsMap中对应的copyDateFormat的值,
        // 根据DateFormat进行转化,默认转化为yyyy-MM-dd HH:mm:ss格式
        if ("java.lang.String".equals(sourceReturnType)
                && (targetReturnType.equals("java.util.Date") || targetReturnType.equals("java.sql.Date"))) {
            // 若时间格式为yyyy-MM-dd格式，则转化为日期格式的Date对象
            if ((null != copyParamsMap
                    && DateTimeUtil.dateFormat.equals(copyParamsMap.get(COPY_DATE_FORMAT)))
                    || dateFormatLists.contains(targetField.getName())) {
                try {
                    value = DateUtil.parseDate((String) value);
                } catch (Exception e) {
                    value = DateUtil.parseDatetime((String) value);
                    log.error(e.getMessage(), e);
                }
            } else {
                value = DateUtil.parseDatetime((String) value);
            }
        } else if ("java.lang.String".equals(sourceReturnType) && targetReturnType.equals("java.lang.Short")) {
            value = (short) Integer.parseInt((String) value);
        } else if ("java.lang.String".equals(sourceReturnType) && targetReturnType.equals("java.lang.Double")) {
            value = Double.valueOf((String) value);
        } else if ("java.lang.String".equals(sourceReturnType) && targetReturnType.equals("java.lang.Boolean")) {
            value = Boolean.valueOf((String) value);
            // 若目标类型为String
        } else if ("java.lang.String".equals(sourceReturnType) && targetReturnType.equals("java.lang.Integer")) {
            value = Integer.valueOf((String) value);
            // 若目标类型为String
        } else if ("java.lang.String".equals(targetReturnType)) {
            value = value.toString();
        } else {
            // 若为对象，则拷贝该value的值为目标对象
            Object targetObj = Class.forName(targetReturnType).newInstance();
            copyProperties(value, targetObj, false, copyParamsMap);
            value = targetObj;
        }

        return value;
    }
}
