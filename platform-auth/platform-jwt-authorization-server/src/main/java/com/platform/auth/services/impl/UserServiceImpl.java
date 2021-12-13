package com.platform.auth.services.impl;

import com.platform.auth.entity.SysUser;
import com.platform.auth.mapper.UserMapper;
import com.platform.auth.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Advance
 * @date 2021年12月10日 15:22
 * @since V1.0.0
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userMapper.findByUsername(username);
        return sysUser;
    }
}
