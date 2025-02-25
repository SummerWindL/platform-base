package com.platform.es.config;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年02月21日 19:01
 */

import com.platform.common.util.JsonAdaptor;
import com.platform.es.config.db.MySqlInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
@Configuration
public class BeanConfig {
    @Bean
    JsonAdaptor jsonAdaptor(){
        JsonAdaptor jsonAdaptor = new JsonAdaptor();
        jsonAdaptor.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        return jsonAdaptor;
    }
    @Bean
    public MySqlInjector sqlInjector() {
        return new MySqlInjector();
    }
}
