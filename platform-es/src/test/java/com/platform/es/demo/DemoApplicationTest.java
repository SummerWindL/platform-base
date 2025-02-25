package com.platform.es.demo;

import com.platform.es.PlatformEsApplication;
import com.platform.es.demo.entity.User;
import com.platform.es.demo.mapper.UserMapper;
import com.platform.es.PlatformEsApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年02月21日 18:42
 */
public class DemoApplicationTest extends PlatformEsApplicationTest {
    // TODO 需要修复
    @Resource
    private UserMapper userMapper;
    @Test
    public void addUser(){
        User user = new User();
        user.setUserName("李彦宏");
        user.setUserId(1);
        user.setPassword("123456");
        user.setAccount("lyh");
        user.setPassword("99999");
        userMapper.insert(user);
    }
}
