package com.platform.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.auth.config.RsaKeyProperties;
import com.platform.auth.entity.SysUser;
import com.platform.auth.util.JwtUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Robod
 * @date 2020/8/10 8:42
 * 检验token过滤器
 */
public class JwtVerifyFilter extends BasicAuthenticationFilter {

    private RsaKeyProperties rsaKeyProperties;

    public JwtVerifyFilter(AuthenticationManager authenticationManager, RsaKeyProperties rsaKeyProperties) {
        super(authenticationManager);
        this.rsaKeyProperties = rsaKeyProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("AixPlatformToken ")) {
            //登录之后从token中获取用户信息
            String token = header.replace("AixPlatformToken ","");
            SysUser sysUser = JwtUtils.getInfoFromToken(token, rsaKeyProperties.getPublicKey(), SysUser.class).getUserInfo();
            if (sysUser != null) {
                Authentication authResult = new UsernamePasswordAuthenticationToken
                        (sysUser.getUsername(),null,sysUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authResult);
                chain.doFilter(request, response);
            }
        }
        chain.doFilter(request, response);
    }
}
