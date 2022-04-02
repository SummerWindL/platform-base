package com.platform.auth.bak.security.config;

import com.platform.auth.bak.security.filter.JwtLoginFilter;
import com.platform.auth.bak.security.filter.JwtVerifyFilter;
import com.platform.auth.bak.security.handler.MyAccessDeniedHandle;
import com.platform.auth.bak.security.handler.MyAuthenticationEntryPoint;
import com.platform.auth.bak.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Advance
 * @date 2021年12月09日 15:09
 * @since V1.0.0
 */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private MyAccessDeniedHandle myAccessDeniedHandle;
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRestfulAccessDeniedHandle(MyAccessDeniedHandle myAccessDeniedHandle) {
        this.myAccessDeniedHandle = myAccessDeniedHandle;
    }

    @Autowired
    public void setRestAuthenticationEntryPoint(MyAuthenticationEntryPoint myAuthenticationEntryPoint) {
        this.myAuthenticationEntryPoint = myAuthenticationEntryPoint;
    }

    @Bean
    public PasswordEncoder myPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Remove the ROLE_ prefix
     */
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // 允许访问
                .antMatchers("/login","/demo-zx-websocket-midserver").permitAll()
                .anyRequest().authenticated() // 其他请求拦截
                .and()
                .csrf().disable() //关闭csrf
                .addFilter(new JwtLoginFilter(super.authenticationManager()))
                .addFilter(new JwtVerifyFilter(super.authenticationManager()))
                .exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandle) // 自定义无权限访问
                .authenticationEntryPoint(myAuthenticationEntryPoint) // 自定义未登录返回
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //禁用session
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // UserDetailsService类
        auth.userDetailsService(userService)
                // 加密策略
                .passwordEncoder(passwordEncoder);

    }

    /**
     * 解决 AuthenticationManager 无法注入的问题
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
