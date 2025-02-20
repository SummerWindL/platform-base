package com.platform.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.platform.core.json.BigDecimalSerializer;
import com.platform.core.json.StringDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Advance
 * @date 2022年07月17日 12:03
 * @since V1.0.0
 */
public class JsonUtils {
    /**
     *
     */
    public static final ObjectMapper OBJECT_MAPPER = createObjectMapper();

    /**
     * 初始化ObjectMapper
     *
     * @return ObjectMapper
     */
    private static ObjectMapper createObjectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();

        // 支持单引号
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 支持fieldName 不带引号
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 设置默认日期格式
        objectMapper.setDateFormat(new SimpleDateFormat(DateUtil.DEFAULT_DATE_FORMAT_EN));

        SimpleModule module = new SimpleModule("jaskson-m");
        module.addSerializer(BigDecimal.class, new BigDecimalSerializer());
//        module.addSerializer(String.class, new StringSerializer());
        module.addSerializer(Date.class, new DateSerializer(true, null));
        module.addDeserializer(String.class, new StringDeserializer());
//        module.addSerializer(Date.class, new DateSerializer(false, new SimpleDateFormat(DateUtil.DEFAULT_DATE_FORMAT_EN)));
        objectMapper.registerModule(module);

        return objectMapper;
    }

    public static String object2Json(Object o) {
        StringWriter sw = new StringWriter();
        JsonGenerator gen = null;
        try {
            gen = new JsonFactory().createGenerator(sw);
            OBJECT_MAPPER.writeValue(gen, o);
        }
        catch (IOException e) {
            throw new RuntimeException("不能序列化对象为Json", e);
        }
        finally {
            if (null != gen) {
                try {
                    gen.close();
                }
                catch (IOException e) {
                    throw new RuntimeException("不能序列化对象为Json", e);
                }
            }
        }
        return sw.toString();
    }

    public static Map<String, Object> stringToMap(String json){
        try {
            return OBJECT_MAPPER.readValue(json, Map.class);
        } catch (Exception e) {
            return new HashMap<String, Object>();
        }
    }
    /**
     * JSON转对象
     * @param jsonNode JSON
     * @return 对象
     */
    public static Object jsonToObject(JsonNode jsonNode) {
        if(JsonNodeType.OBJECT.equals(jsonNode.getNodeType())) {
            Map<String, Object> map = new HashMap<>();
            Iterator<String> fieldNames = jsonNode.fieldNames();
            while(fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                map.put(fieldName, jsonToObject(jsonNode.path(fieldName)));
            }
            return map;
        }
        if(JsonNodeType.ARRAY.equals(jsonNode.getNodeType())) {
            List<Object> list = new ArrayList<>();
            ArrayNode arrayNode = toArrayNode(jsonNode);
            for(int i=0; i < arrayNode.size(); i++) {
                list.add(jsonToObject(arrayNode.get(i)));
            }
            return list;
        }
        if(JsonNodeType.STRING.equals(jsonNode.getNodeType())) {
            return jsonNode.asText(StringUtils.EMPTY);
        }
        if(jsonNode.isBoolean()) {
            return jsonNode.booleanValue();
        }
        if(jsonNode.isFloat()) {
            return jsonNode.floatValue();
        }
        if(jsonNode.isDouble()) {
            return jsonNode.doubleValue();
        }
        if(jsonNode.isLong()) {
            return jsonNode.longValue();
        }
        if(jsonNode.isInt()) {
            return jsonNode.intValue();
        }
        if(jsonNode.isShort()) {
            return jsonNode.shortValue();
        }
        if(jsonNode.isNumber()) {
            return jsonNode.numberValue();
        }
        return jsonNode.toString();
    }

    /**
     * 将节点转换为数组节点
     *
     * @param jsonNode 节点对象
     * @return 数组节点
     */
    public static ArrayNode toArrayNode(JsonNode jsonNode) {
        ArrayNode arrayNode = null;
        if (jsonNode != null && !jsonNode.isNull() && !jsonNode.isMissingNode()) {
            if (jsonNode.isArray()) {
                arrayNode = (ArrayNode)jsonNode;
            }
            else {
                arrayNode = OBJECT_MAPPER.createArrayNode();
                arrayNode.add(jsonNode);
            }
        }
        return arrayNode;
    }

    /**
     * 将字符串转换为一个json节点
     *
     * @param content 源字符串
     * @return json节点
     */
    public static JsonNode toJsonNode(String content) {
        JsonNode jsonNode = null;
        try {
            if (StringUtil.isNotEmpty(content)) {
                jsonNode = OBJECT_MAPPER.readTree(content);
            }
        }
        catch (Exception e) {
            // e.printStackTrace();
        }
        return jsonNode;
    }

    /**
     * @param map map对象
     * @param beanClass 目标类型
     * @return Object
     * @throws Exception 异常
     */
    @SuppressWarnings("unchecked")
    public static Object map2entity(Map<String, Object> map, Class<?> beanClass)
            throws Exception {

        Object newEntity = beanClass.newInstance();

        Field[] fields = newEntity.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            String className = field.getType().getName();
            Object value = map.get(field.getName());
            field.setAccessible(true);
            if (className.equalsIgnoreCase("java.util.List")) { // 处理List类型
                ParameterizedType type = (ParameterizedType)field.getGenericType();
                Type subType = type.getActualTypeArguments()[0];
                if (value instanceof List
                        && subType.getTypeName().startsWith("com.joyintech")) {
                    List<Object> array = new ArrayList<>();
                    List<Map<String, Object>> valueList = (ArrayList<Map<String, Object>>)value;
                    for (Map<String, Object> temp : valueList) {
                        array.add(map2entity(temp, Class.forName(subType.getTypeName())));
                    }
                    field.set(newEntity, array);
                    map.remove(field.getName());
                }
            } // 以com.joyintech.tams开头的，则可以判断为自定义对象
            else if (className.startsWith("com.joyintech")) {
                if (value instanceof Map) {
                    field.set(newEntity,
                            map2entity((Map<String, Object>)value, Class.forName(className)));
                    map.remove(field.getName());
                }
            }
        }

        BeanUtil.copyPropertiesIgnoreNull(newEntity, BeanUtil.mapToObject(map, beanClass));

        return newEntity;
    }

    /**
     * 将对象串格式化为JSON字符串
     *
     * @param objects 对象数组
     * @return JSON字符串
     */
    public static String objectArray2Json(Object... objects) {
        String result = "[";
        try {
            for (Object object : objects) {
                if (!result.equals("[")) {
                    result += ",";
                }
                result += OBJECT_MAPPER.writeValueAsString(object);
            }
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result + "]";
    }

    /**
     * @param o Object
     * @return Map<String, Object>
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> object2Map(Object o) {
        return OBJECT_MAPPER.convertValue(o, Map.class);
    }

    /**
     * 将 json 字段串转换为 对象.
     *
     * @param json 字符串
     * @param clazz 需要转换为的类
     * @param <T>
     * @return T
     */
    public static <T> T json2Object(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        }
        catch (IOException e) {
            throw new RuntimeException("将 Json 转换为对象时异常,数据是:" + json, e);
        }
    }

    /**
     * 将 json 字段串转换为 List.
     *
     * @param json json字符串
     * @param clazz 类
     * @param <T>
     * @return List
     * @throws IOException 异常
     */
    public static <T> List<T> json2List(String json, Class<T> clazz)
            throws IOException {
        JavaType type = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);

        List<T> list = OBJECT_MAPPER.readValue(json, type);
        return list;
    }

    /**
     * 将 json 字段串转换为 数据.
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T[] json2Array(String json, Class<T[]> clazz)
            throws IOException {
        return OBJECT_MAPPER.readValue(json, clazz);

    }

    public static <T> T node2Object(JsonNode jsonNode, Class<T> clazz) {
        try {
            T t = OBJECT_MAPPER.treeToValue(jsonNode, clazz);
            return t;
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException("将 Json 转换为对象时异常,数据是:" + jsonNode.toString(), e);
        }
    }

    public static JsonNode object2Node(Object o) {
        try {
            if (o == null) {
                return OBJECT_MAPPER.createObjectNode();
            }
            else {
                return OBJECT_MAPPER.convertValue(o, JsonNode.class);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("不能序列化对象为Json", e);
        }
    }
}
