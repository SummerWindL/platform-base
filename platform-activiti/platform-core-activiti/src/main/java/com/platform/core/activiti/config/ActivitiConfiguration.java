package com.platform.core.activiti.config;

import com.platform.common.util.JsonAdaptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Advance
 * @date 2021年12月06日 9:42
 * @since V1.0.0
 */
@Configuration
public class ActivitiConfiguration {

    @Bean
    public JsonAdaptor getJsonAdaptor(){
        return new JsonAdaptor();
    }
}
