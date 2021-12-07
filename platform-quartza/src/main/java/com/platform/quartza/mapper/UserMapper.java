package com.platform.quartza.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.Page;
import com.platform.quartza.base.repo.BaseMapper;
import com.platform.quartza.entity.User;

/**
 * @author Advance
 * @date 2021年12月03日 17:00
 * @since V1.0.0
 */
@DS("master")
public interface UserMapper extends BaseMapper<User> {
    Page<User> getUsers();
}
