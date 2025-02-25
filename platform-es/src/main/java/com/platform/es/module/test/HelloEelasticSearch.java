package com.platform.es.module.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年02月21日 16:39
 */
@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloEelasticSearch {
    @GetMapping("/es")
    public String helloEs(){
        log.info("你好es");
        return "es";
    }
}
