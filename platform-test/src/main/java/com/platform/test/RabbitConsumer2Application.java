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
//public class RabbitConsumer2Application {
//    public static void main(String[] args) {
//        SpringApplication.run(RabbitConsumer2Application.class, args);
//    }
//
//    @Configuration
//    @ConditionalOnProperty(prefix = "spring", name = "profiles.active", havingValue = "consumer2")
//    class MessageReceiverConfig {
//        //配置监听的哪一个队列，同时在没有queue和exchange的情况下会去创建并建立绑定关系
////    @RabbitListener(bindings = @QueueBinding(
////            value = @Queue(value = "topic.test.hello", durable = "true"),
////            exchange = @Exchange(value="exchange",durable = "true",type = "topic"),
////            key = "topic.test.hello"
////    ))
//        @RabbitListener(queues = "queue.test.topic")
//        public void process(SimpleRabbitMessage context, Message message, Channel channel) throws IOException {
//            MessageProperties properties = message.getMessageProperties();
//            //消费者操作
//            System.out.println("---------消费者2：收到消息，开始消费---------");
//            System.out.println("消息ID：" + context.getId());
//            System.out.println(context.getMessage());
//        }
//
//        @RabbitListener(queues = "queue.test.fanout.2")
//        public void processFanout(SimpleRabbitMessage context, Message message, Channel channel) throws Exception {
//            MessageProperties properties = message.getMessageProperties();
//            System.out.println("消费者2:processFanout:" + context.getId());
//
//            RabbitUtils.ack(channel, message);
//        }
//    }
//}
