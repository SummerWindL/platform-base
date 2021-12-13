package com.platform.auth.config;

import com.platform.auth.filter.JwtVerifyFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @author Advance
 * @date 2021年12月13日 11:33
 * @since V1.0.0
 */
@Configuration
@EnableWebSecurity      //加了这个注解才能写SpringSecurity相关的配置
@EnableGlobalMethodSecurity(securedEnabled = true)  //开启权限控制的注解支持,securedEnabled表示SpringSecurity内部的权限控制注解开关
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final RsaKeyProperties rsaKeyProperties;

    public WebSecurityConfig(RsaKeyProperties rsaKeyProperties) {
        this.rsaKeyProperties = rsaKeyProperties;
    }

    /**
     * 配置SpringSecurity相关信息
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()  //关闭csrf
                .authorizeRequests()
                .antMatchers("/**").hasAnyRole("USER") //角色信息
                .anyRequest()   //其它资源
                .authenticated()    //表示其它资源认证通过后
                .and()
                .addFilter(new JwtVerifyFilter(super.authenticationManager(),rsaKeyProperties))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);    //禁用session
    }
}
