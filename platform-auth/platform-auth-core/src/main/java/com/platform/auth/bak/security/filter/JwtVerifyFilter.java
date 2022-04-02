package com.platform.auth.bak.security.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 请求校验
 * @author Advance
 * @date 2021年12月09日 15:35
 * @since V1.0.0
 */
public class JwtVerifyFilter extends BasicAuthenticationFilter {
    public JwtVerifyFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (header != null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            // 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头
            authorities.add(new SimpleGrantedAuthority("user:resource"));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                    ("admin",null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }
        chain.doFilter(request, response);
    }

}
