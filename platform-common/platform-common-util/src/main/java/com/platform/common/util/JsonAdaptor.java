package com.platform.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @ClassName JsonAdaptor
 * @Description
 * @Author yanl
 * @Date 2020/8/25 0:39
 * @Version 1.0
 **/
public class JsonAdaptor extends ObjectMapper {

    /**
     *
     */
    private static final long serialVersionUID = -7827059945208682565L;

    public JsonAdaptor() {
        super();
        // 允许单引号
        this.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 数字加双引号
        // this.configure(Feature.WRITE_NUMBERS_AS_STRINGS, true);
        // 允许不明字段
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
        // 空字符串转null
        this.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // null转为""
        this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object obj, JsonGenerator gen, SerializerProvider provider)
                    throws IOException, JsonProcessingException {
                // gen.writeString("");
                gen.writeNull();
            }
        });

    }
}
