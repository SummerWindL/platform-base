package com.platform.es.module.kafka.customer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaCustomer {

    /**
     * 定义此消费者接收topics = "first"的消息，与controller中的topic对应上即可
     * @param record 变量代表消息本身，可以通过ConsumerRecord<?,?>类型的record变量来打印接收的消息的各种信息
     */
    @KafkaListener(topics = "kafka_text_topic")
    public void userListener(ConsumerRecord<?,?> record) {
        log.error("topic: " + record.topic()
                + "--> offset:" + record.offset()
                + "msg: " + record.value());
    }

}
