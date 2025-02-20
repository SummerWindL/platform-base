//package com.platform.test;
//
//import com.platform.test.producer.DemoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author heyangyang
// */
//@EnableScheduling
//@EnableAsync
//@EnableAspectJAutoProxy(proxyTargetClass = true)
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, EurekaClientAutoConfiguration.class})
//public class RabbitProducerApplication {
//    public static void main(String[] args) {
//        SpringApplication.run(RabbitProducerApplication.class, args);
//    }
//
//    @Autowired
//    private DemoService service;
//
//    @Configuration
//    @RestController
//    @ConditionalOnProperty(prefix = "spring", name = "profiles.active", havingValue = "producer")
//    class MessageSenderConfig {
//
//        @GetMapping("/rabbit/send")
//        public String createMessage() {
//            service.createMessage();
//            return "ok";
//        }
//
////        @Scheduled(fixedRate=1000)
//        public void send() {
//            service.createMessage();
//        }
//    }
//}
