package com.platform.auth.bak.security.handler;

import cn.hutool.json.JSONUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 当访问接口没有权限时主要实现springsecurity给我们提供的 AccessDeniedHandler接口，自定义的返回结果
 * @author Advance
 * @date 2021年12月09日 15:44
 * @since V1.0.0
 */
@Component
public class MyAccessDeniedHandle implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        // 这里写死只做测试  请以实际为主
        Map<String, Object> map = new HashMap<>();
        map.put("code", 501);
        map.put("msg", "您没有权限");
        response.getWriter().println(JSONUtil.parse(map));
        response.getWriter().flush();
    }
}
