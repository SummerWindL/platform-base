package com.platform.auth.bak.security.service.impl;

import com.platform.auth.bak.security.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Advance
 * @date 2021年12月09日 15:14
 * @since V1.0.0
 */
//@Service
public class UserServiceImpl implements UserService {
    @Resource
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        // 临时写死 这里是数据库查询出来的
        return User.builder().username("admin")
                .password(passwordEncoder.encode("123456"))
                .authorities(authorities).build();
    }
}
