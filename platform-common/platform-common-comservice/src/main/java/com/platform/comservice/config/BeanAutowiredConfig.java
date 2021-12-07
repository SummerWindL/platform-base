package com.platform.comservice.config;

import com.platform.common.util.JsonAdaptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : yanl
 * @version V1.0
 * @Description:
 * @date : 2021/9/3
 */
@Configuration
public class BeanAutowiredConfig {

    @Bean
    @ConditionalOnMissingBean(JsonAdaptor.class)
    JsonAdaptor jsonAdaptor() {
        return new JsonAdaptor();
    }
}
