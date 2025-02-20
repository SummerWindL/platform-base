package com.platform.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 在Controller中使用，设置当前画面的ID
 *
 * @version 1.0 2018年1月26日
 * @see PageId
 * @since 1.0
 */
@Documented
@Retention(RUNTIME)
@Target({METHOD,FIELD})
public @interface PrimaryKey {
    public enum Field{
        PK_1,PK_2,PK_3,PK_4,PK_5,PK_6,PK_7,PK_8,PK_9,PK_10
    };
    Field value();
}
