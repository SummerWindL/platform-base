package com.platform.common.entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.platform.common.util.StringUtil;

import java.io.IOException;

/**
 * @author Advance
 * @date 2022年03月04日 10:38
 * @since V1.0.0
 */
public class StringDeserializer extends  com.fasterxml.jackson.databind.deser.std.StringDeserializer {

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return StringUtil.trimHtmlTag(super.deserialize(p, ctxt));
    }

    @Override
    public String deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return StringUtil.trimHtmlTag(super.deserializeWithType(p, ctxt, typeDeserializer));
    }
}
