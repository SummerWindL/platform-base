package com.platform.data.config;

import com.platform.data.jdbc.plugin.SqlLogInterceptor;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Advance
 * @date 2022年01月18日 14:59
 * @since V1.0.0
 */
@Configuration
public class DataSourceConfiguration {
    @Bean
    ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.addInterceptor(new SqlLogInterceptor());
            }
        };
    }
}
