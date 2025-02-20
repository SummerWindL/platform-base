//package com.platform.test.producer;
//
//import com.platform.core.rabbitmq.annotation.MessageSender;
//import com.platform.rabbitmq.entity.SimpleRabbitMessage;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.atomic.AtomicInteger;
//
//@Service
//public class DemoService {
//
//    private AtomicInteger count = new AtomicInteger(0);
//
//    @MessageSender(exchange = "exchange.topic", routingKey = "invest.product.create")
//    public SimpleRabbitMessage createMessage() {
//        int index = count.incrementAndGet();
//        SimpleRabbitMessage defaultRabbitMessage = new SimpleRabbitMessage();
//        defaultRabbitMessage.setId("msg-" + index);
//        defaultRabbitMessage.setMessage("hello " + index);
//        return defaultRabbitMessage;
//    }
//}
