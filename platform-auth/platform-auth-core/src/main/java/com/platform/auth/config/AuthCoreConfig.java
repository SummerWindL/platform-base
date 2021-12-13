package com.platform.auth.config;

import com.platform.common.util.JsonAdaptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * @author Advance
 * @date 2021年12月13日 11:37
 * @since V1.0.0
 */
@Configuration
public class AuthCoreConfig {
    @Bean
    JsonAdaptor jsonAdaptor(){
        JsonAdaptor jsonAdaptor = new JsonAdaptor();
        jsonAdaptor.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        return jsonAdaptor;
    }
}
