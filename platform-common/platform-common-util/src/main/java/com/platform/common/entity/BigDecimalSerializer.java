package com.platform.common.entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author Advance
 * @date 2022年03月04日 10:37
 * @since V1.0.0
 */
public class BigDecimalSerializer extends JsonSerializer<BigDecimal> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        if (value == null) {
            gen.writeString(StringUtils.EMPTY);
        }
        else {
            gen.writeString(value.stripTrailingZeros().toPlainString());
        }
    }
}
