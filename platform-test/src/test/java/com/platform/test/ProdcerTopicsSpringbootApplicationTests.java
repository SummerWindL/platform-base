package com.platform.test;

import com.platform.test.config.RabbitmqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @author Advance
 * @date 2022年10月05日 15:35
 * @since V1.0.0
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
public class ProdcerTopicsSpringbootApplicationTests {

    private RabbitTemplate rabbitTemplate;


    public RabbitTemplate getRabbitTemplate() {
        return rabbitTemplate;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Test
    public void Producer_topics_springbootTest() {

        //使用rabbitTemplate发送消息
        String message = "234";
        /**
         * 参数：
         * 1、交换机名称
         * 2、routingKey
         * 3、消息内容
         */
//        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM, "inform.email", message);
        int i = 0;
        while(true){
            rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM, "inform.datafiles", new Date());
            i++;
        }

    }

}
