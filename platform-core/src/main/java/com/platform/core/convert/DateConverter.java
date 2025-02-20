package com.platform.core.convert;

import org.apache.commons.beanutils.converters.DateTimeConverter;

import java.util.Date;

/**
 * @author Advance
 * @date 2022年06月23日 14:54
 * @since V1.0.0
 */
public class DateConverter extends DateTimeConverter {

    /**
     * 日期时间格式yyyy-MM-dd HH:mm:ss
     */
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期时间格式yyyy-MM-dd HH:mm
     */
    public static final String DATETIME_PATTERN_NO_SECOND = "yyyy-MM-dd HH:mm";

    /**
     * 年月日yyyy-MM-dd
     */
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 年月yyyy-MM
     */
    public static final String MONTH_PATTERN = "yyyy-MM";

    /**
     * Construct a <b>java.util.Date</b> <i>Converter</i> that throws a
     * <code>ConversionException</code> if an error occurs.
     */
    public DateConverter() {
        super(null);
        this.setPatterns(new String[]{DATE_PATTERN, MONTH_PATTERN,
                DATETIME_PATTERN_NO_SECOND, DATETIME_PATTERN});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<?> getDefaultType() {
        return Date.class;
    }
}
