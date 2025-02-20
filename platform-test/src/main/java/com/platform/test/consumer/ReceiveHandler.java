package com.platform.test.consumer;

import com.platform.test.config.RabbitmqConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author Advance
 * @date 2022年10月05日 15:38
 * @since V1.0.0
 */
@Component
public class ReceiveHandler {
    //监听email队列
//    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_EMAIL})
//    public void receive_email(Object msg, Message message, Channel channel){
//        System.out.println("QUEUE_INFORM_EMAIL msg"+msg);
//    }
//    //监听sms队列
//    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_SMS})
//    public void receive_sms(Object msg, Message message, Channel channel){
//        System.out.println("QUEUE_INFORM_SMS msg"+msg);
//    }
    //监听email队列
    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_DATAFILES})
    public void receive_email(Object msg, Message message, Channel channel){
        System.out.println("QUEUE_INFORM_DATAFILES msg"+msg);
    }

}
