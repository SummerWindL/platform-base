package com.platform.core.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.platform.core.util.DateUtil;
import com.platform.core.util.StringUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Advance
 * @date 2022年07月17日 11:40
 * @since V1.0.0
 */
public class CustomDateDeserialize extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {

        Date date = null;
        String text = jp.getText();
        try {
            if (StringUtil.isNotEmpty(text)) {
                date = new Date(Long.parseLong(text));
            }
        }
        catch (NumberFormatException e) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DEFAULT_DATE_FORMAT_EN);
                date = sdf.parse(text);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return date;
    }
}