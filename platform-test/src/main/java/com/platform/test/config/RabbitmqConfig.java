package com.platform.test.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ObjectToStringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Advance
 * @date 2022年10月05日 15:34
 * @since V1.0.0
 */
@Configuration
public class RabbitmqConfig {
//    public static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
//    public static final String QUEUE_INFORM_SMS = "queue_inform_sms";
//    public static final String EXCHANGE_TOPICS_INFORM="exchange_topics_inform";
//    public static final String ROUTINGKEY_EMAIL="inform.#.email.#";
//    public static final String ROUTINGKEY_SMS="inform.#.sms.#";
//
//    @Bean(name = "restTemplate")
//    public RestTemplate restTemplate() {
//        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
//        factory.setReadTimeout(60000);// 单位为ms
//        factory.setConnectTimeout(60000);// 单位为ms
//        RestTemplate restTemplate = new RestTemplate(factory);
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//        messageConverters.add(new SourceHttpMessageConverter<>());
//        messageConverters.add(new FormHttpMessageConverter());
//        messageConverters.add(new MappingJackson2HttpMessageConverter());
//        messageConverters.add(
//                new ObjectToStringHttpMessageConverter(new GenericConversionService()));
//        restTemplate.setMessageConverters(messageConverters);
//        return restTemplate;
//    }
//    //声明交换机
//    @Bean(EXCHANGE_TOPICS_INFORM)
//    public Exchange EXCHANGE_TOPICS_INFORM(){
//        //durable(true) 持久化，mq重启之后交换机还在
//        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFORM).durable(true).build();
//    }
//
//    //声明QUEUE_INFORM_EMAIL队列
//    @Bean(QUEUE_INFORM_EMAIL)
//    public Queue QUEUE_INFORM_EMAIL(){
//        return new Queue(QUEUE_INFORM_EMAIL);
//    }
//    //声明QUEUE_INFORM_SMS队列
//    @Bean(QUEUE_INFORM_SMS)
//    public Queue QUEUE_INFORM_SMS(){
//        return new Queue(QUEUE_INFORM_SMS);
//    }
//
//    //ROUTINGKEY_EMAIL队列绑定交换机，指定routingKey
//    @Bean
//    public Binding BINDING_QUEUE_INFORM_EMAIL(@Qualifier(QUEUE_INFORM_EMAIL) Queue queue,
//                                              @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange){
//        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_EMAIL).noargs();
//    }
//    //ROUTINGKEY_SMS队列绑定交换机，指定routingKey
//    @Bean
//    public Binding BINDING_ROUTINGKEY_SMS(@Qualifier(QUEUE_INFORM_SMS) Queue queue,
//                                          @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange){
//        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_SMS).noargs();
//    }

    public static final String QUEUE_INFORM_DATAFILES = "queue_inform_datafiles";
    public static final String EXCHANGE_TOPICS_INFORM="exchange_topics_inform";
    public static final String ROUTINGKEY_DATAFILES="inform.#.datafiles.#";

    @Bean(name = "restTemplate")
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(60000);// 单位为ms
        factory.setConnectTimeout(60000);// 单位为ms
        RestTemplate restTemplate = new RestTemplate(factory);
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new SourceHttpMessageConverter<>());
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        messageConverters.add(
                new ObjectToStringHttpMessageConverter(new GenericConversionService()));
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        /**
         * 当mandatory标志位设置为true时
         * 如果exchange根据自身类型和消息routingKey无法找到一个合适的queue存储消息
         * 那么broker会调用basic.return方法将消息返还给生产者
         * 当mandatory设置为false时，出现上述情况broker会直接将消息丢弃
         */
        rabbitTemplate.setMandatory(true);
        //rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean("mqContainerFactory")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public SimpleRabbitListenerContainerFactory containerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //factory.setMessageConverter(messageConverter);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    //声明交换机
    @Bean(EXCHANGE_TOPICS_INFORM)
    public Exchange EXCHANGE_TOPICS_INFORM(){
        //durable(true) 持久化，mq重启之后交换机还在
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFORM).durable(true).build();
    }

    //声明QUEUE_INFORM_DATAFILES队列
    @Bean(QUEUE_INFORM_DATAFILES)
    public Queue QUEUE_INFORM_FILES(){
        return new Queue(QUEUE_INFORM_DATAFILES);
    }

    //ROUTINGKEY_DATAFILES队列绑定交换机，指定routingKey
    @Bean
    public Binding BINDING_QUEUE_INFORM_FILES(@Qualifier(QUEUE_INFORM_DATAFILES) Queue queue,
                                              @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_DATAFILES).noargs();
    }

}
