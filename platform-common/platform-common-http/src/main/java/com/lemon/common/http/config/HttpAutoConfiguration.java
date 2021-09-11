package com.lemon.common.http.config;

import com.lemon.common.http.client.HttpClientService;
import com.lemon.common.http.client.impl.ApacheHttpClientServiceImpl;
import com.platform.common.util.JsonAdaptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-07-03 11:03
 **/
@Configuration
@ConditionalOnClass({ApacheHttpClientServiceImpl.class})
public class HttpAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(HttpClientService.class)
    ApacheHttpClientServiceImpl apacheHttpClient(){
        return new ApacheHttpClientServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(JsonAdaptor.class)
    JsonAdaptor jsonAdaptor(){
        return new JsonAdaptor();
    }
}
