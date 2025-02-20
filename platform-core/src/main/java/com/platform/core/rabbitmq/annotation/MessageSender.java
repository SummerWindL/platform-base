package com.platform.core.rabbitmq.annotation;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MessageSender {
    /**
     * 交换机
     * @return {@code exchange}
     */
    String exchange() default "";

    /**
     * 路由键
     * @return {@code routingKey}
     */
    String routingKey() default "";
}
