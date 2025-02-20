//package com.platform.test;
//
//import com.platform.rabbitmq.entity.SimpleRabbitMessage;
//import com.platform.rabbitmq.util.RabbitUtils;
//import com.rabbitmq.client.Channel;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessageProperties;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.annotation.EnableScheduling;
//
//import java.io.IOException;
//
///**
// * @author heyangyang
// */
//@EnableScheduling
//@EnableAsync
//@EnableAspectJAutoProxy(proxyTargetClass = true)
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, EurekaClientAutoConfiguration.class})
//public class RabbitConsumer1Application {
//    public static void main(String[] args) {
//        SpringApplication.run(RabbitConsumer1Application.class, args);
//    }
//
//
//    @Configuration
//    @ConditionalOnProperty(prefix = "spring", name = "profiles.active", havingValue = "consumer1")
//    class MessageReceiverConfig {
//        /**
//         * 配置监听的哪一个队列，同时在没有queue和exchange的情况下会去创建并建立绑定关系
//         */
//        public void process(SimpleRabbitMessage context, Message message, Channel channel) throws IOException {
//            MessageProperties properties = message.getMessageProperties();
//            //消费者操作
//            System.out.println("---------消费者1 收到消息，开始消费---------");
//            System.out.println("消息ID："+context.getId());
//
////        long deliveryTag = properties.getDeliveryTag();
//
//            // 模拟一个异常信息，不允许捕获
////        System.out.println(1/0);
//            RabbitUtils.ack(channel, message);
//        }
//
//        @RabbitListener(queues = "queue.test.fanout.1")
//        public void processFanout(SimpleRabbitMessage context, Channel channel, Message message) throws IOException {
//            System.out.println("消费者1:processFanout:" + context.getId());
//            // 模拟死信
////        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
//            RabbitUtils.ack(channel, message);
//        }
//    }
//}
