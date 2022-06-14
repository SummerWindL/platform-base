package com.platform.comservice.door.annotation;

import java.lang.annotation.*;

/**
 * 门面模式
 * @author Advance
 * @date 2022/4/12 10:21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DoDoor {
    String key() default "";

    String returnJson() default "";

    Class<?> value();

    Class<?>[] data() default {};
}
