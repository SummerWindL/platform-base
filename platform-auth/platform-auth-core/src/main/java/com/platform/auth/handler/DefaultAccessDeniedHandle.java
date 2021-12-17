package com.platform.auth.handler;

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
 * 当用户无权限是默认返回处理
 * @author Advance
 * @date 2021年12月14日 11:24
 * @since V1.0.0
 */
@Component
public class DefaultAccessDeniedHandle implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        // 这里写死只做测试  请以实际为主
        Map<String, Object> map = new HashMap<>();
        map.put("code", 501);
        map.put("msg", "您没有权限!");
        response.getWriter().println(JSONUtil.parse(map));
        response.getWriter().flush();
    }
}
