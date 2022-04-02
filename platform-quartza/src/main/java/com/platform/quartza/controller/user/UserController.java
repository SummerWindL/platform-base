package com.platform.quartza.controller.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platform.quartza.entity.User;
import com.platform.quartza.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Advance
 * @date 2021年12月03日 17:05
 * @since V1.0.0
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/user/{id}")
    public User selectUserById(@PathVariable("id") Long id){
        return userService.selectById(id);
    }

    @GetMapping("/users")
    public PageInfo<User> lists(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        PageInfo<User> pageInfo = new PageInfo<>(userService.getUsers());
        return pageInfo;
    }

    @GetMapping("/users/getUser/{userId}")
    public PageInfo<User> getUser(@RequestParam(defaultValue = "1") int pageNo,
                                  @RequestParam(defaultValue = "10") int pageSize,
                                  @PathVariable("userId") int userId){
        PageHelper.startPage(pageNo,pageSize);
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("userId",userId);
        PageInfo<User> pageInfo = new PageInfo<>(userService.getUserByUserId(params));
        return pageInfo;
    }


}
