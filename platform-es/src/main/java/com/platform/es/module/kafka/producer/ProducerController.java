package com.platform.es.module.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/kafka")
public class ProducerController {

    @Resource
    private KafkaTemplate<Object, Object> kafkaTemplate;

    @GetMapping("/send")
    public void  sendMsg () {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String message = "Producer 发送到 Kafka 的消息 --> ";
                for (int i = 1;i <= 5;i++) {
                    kafkaTemplate.send("kafka_text_topic", "发送到Kafka的消息" + i);
//                    System.out.println(message + i);
                    log.info(message + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();

        System.out.println("kafka消息已开始发送");
    }
}
