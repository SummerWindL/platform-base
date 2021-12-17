package com.platform.auth.filter;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.auth.config.AuthCoreConfig;
import com.platform.auth.config.RsaKeyProperties;
import com.platform.auth.entity.SysRole;
import com.platform.auth.entity.SysUser;
import com.platform.auth.util.JwtUtils;
import com.platform.common.util.JsonAdaptor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Advance
 * @date 2020/8/10 7:54
 * 认证过滤器
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private static JsonAdaptor jsonAdaptor = getJsonAdaptor();

    private static JsonAdaptor getJsonAdaptor() {
        JsonAdaptor jsonAdaptor = new JsonAdaptor();
        jsonAdaptor.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        return jsonAdaptor;
    }

    private AuthenticationManager authenticationManager;
    private RsaKeyProperties rsaKeyProperties;
    private AuthCoreConfig authCoreConfig;

    public JwtLoginFilter(AuthenticationManager authenticationManager, RsaKeyProperties rsaKeyProperties) {
        this.authenticationManager = authenticationManager;
        this.rsaKeyProperties = rsaKeyProperties;
    }

    public JwtLoginFilter(AuthenticationManager authenticationManager, RsaKeyProperties rsaKeyProperties,AuthCoreConfig authCoreConfig) {
        this.authenticationManager = authenticationManager;
        this.rsaKeyProperties = rsaKeyProperties;
        this.authCoreConfig = authCoreConfig;
    }

    /**
     * 这个方法是用来去尝试验证用户的，父类中是从POST请求的form表单中获取，但是这里不是，所以需要重写
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            SysUser user = JSONObject.parseObject(request.getInputStream(),SysUser.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                           user.getUsername(),
                           user.getPassword())
            );
        } catch (Exception e) {
            try {
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                PrintWriter out = response.getWriter();
                Map<String, Object> map = new HashMap<>();
                map.put("code", HttpServletResponse.SC_UNAUTHORIZED);
                map.put("message", "账号或密码错误！");
                out.write(new ObjectMapper().writeValueAsString(map));
                out.flush();
                out.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 成功之后执行的方法，父类中是放入session，不符合我们的要求，所以重写
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(authResult.getName());
        sysUser.setRoles((List<SysRole>) authResult.getAuthorities());
        String token = JwtUtils.generateTokenExpireInMinutes(sysUser,rsaKeyProperties.getPrivateKey(),authCoreConfig.getTokenExpire());
        response.addHeader("Authorization", "AixPlatformToken " + token);
        try {
            //登录成功时，返回json格式进行提示
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            Map<String, Object> map = new HashMap<String, Object>(4);
            map.put("code", HttpServletResponse.SC_OK);
            map.put("message", "登陆成功！");
            out.write(jsonAdaptor.writeValueAsString(map));
            out.flush();
            out.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 一旦调用 springSecurity认证失败 ，立即执行该方法
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException {
        //登录失败时，返回json格式进行提示
        Map<String, Object> map = new HashMap<String, Object>(4);
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        PrintWriter out = response.getWriter();
        if (ex instanceof BadCredentialsException) {
            map.put("code", HttpServletResponse.SC_BAD_GATEWAY);
            map.put("message", "账号或密码错误！");
        }else {
            // 这里还有其他的 异常 。。 比如账号锁定  过期 等等。。。
            map.put("code", HttpServletResponse.SC_BAD_GATEWAY);
            map.put("message", "登陆失败！");
        }
        out.write(new ObjectMapper().writeValueAsString(map));
        response.getWriter().println(JSONUtil.parse(map));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
