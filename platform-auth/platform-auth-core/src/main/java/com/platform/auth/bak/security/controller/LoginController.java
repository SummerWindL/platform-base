package com.platform.auth.bak.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Advance
 * @date 2021年12月08日 17:38
 * @since V1.0.0
 */
@Slf4j
@RestController
public class LoginController {

    /**
     * 登录
     * @author Advance
     * @date 2021/12/8 17:39
     * @return java.lang.String
     */
    @RequestMapping("login")
    public String login(){
        log.info("执行登录方法");
        return "redirect:main.html";
    }
}
