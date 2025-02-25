package com.platform.es.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Advance
 * @date 2022年06月20日 10:42
 * @since V1.0.0
 */
@Data
@Configuration
//@PropertySource("classpath:es-config.properties")
@ConfigurationProperties(prefix = "es")
public class RestHighLevelClientConfig {
    private Logger log = LoggerFactory.getLogger(RestHighLevelClientConfig.class);
    //@Value("${es.host}")
    private String host;
    //@Value("${es.port}")
    private int port;
    //@Value("${es.scheme}")
    private String scheme;
    //@Value("${es.token}")
    private String token;
    //@Value("${es.charset}")
    private String charSet;
    //@Value("${es.client.connectTimeOut}")
    private int connectTimeOut;
    //@Value("${es.client.socketTimeout}")
    private int socketTimeout;

    @Bean
    public RestClientBuilder restClientBuilder() {
        RestClientBuilder restClientBuilder = RestClient.builder(
                new HttpHost(host, port, scheme)
        );

        Header[] defaultHeaders = new Header[]{
                new BasicHeader("Accept", "*/*"),
                new BasicHeader("Charset", charSet),
                //设置token 是为了安全 网关可以验证token来决定是否发起请求 我们这里只做象征性配置
                new BasicHeader("E_TOKEN", token)
        };
        restClientBuilder.setDefaultHeaders(defaultHeaders);
        restClientBuilder.setFailureListener(new RestClient.FailureListener(){
            @Override
            public void onFailure(Node node) {
                log.error("监听 [{}] es节点失败",node.getHost());
            }
        });
        restClientBuilder.setRequestConfigCallback(builder ->
                builder.setConnectTimeout(connectTimeOut).setSocketTimeout(socketTimeout));
        return restClientBuilder;
    }

    @Bean
    public RestHighLevelClient restHighLevelClient(RestClientBuilder restClientBuilder) {
        return new RestHighLevelClient(restClientBuilder);
    }
}
