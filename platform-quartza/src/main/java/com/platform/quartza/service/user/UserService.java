package com.platform.quartza.service.user;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.Page;
import com.platform.quartza.entity.User;
import com.platform.quartza.mapper.UserMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: Advance
 * @create: 2021-12-03 17:04
 * @since V1.0.0
 */
@Component
@DS("master")
public class UserService {
    @Resource
    private UserMapper userMapper;

    public User selectById(long id){
        return userMapper.selectByPrimaryKey(id);
    }

    public Page<User> getUsers() {
        return userMapper.getUsers();
    }

    public Page<User> getUserByUserId(Map<String,Object> params){
        return userMapper.getUserByUserId(params);
    }
}
