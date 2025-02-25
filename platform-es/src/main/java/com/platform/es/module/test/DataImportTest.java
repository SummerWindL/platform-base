package com.platform.es.module.test;

import com.platform.es.demo.entity.User;
import com.platform.es.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 批量数据插入测试 Sharding-jdbc 分库分表
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年02月24日 9:21
 */
@RestController
@RequestMapping("/dataImport")
@Slf4j
public class DataImportTest {
    @Autowired
    private UserService userService;

    @GetMapping("/es")
    private void testBatchInsert() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            User user = new User();
            user.setUserName("李彦宏_"+i);
            user.setPassword("123456");
            user.setAccount("lyh_"+i);
            user.setPassword("99999");
            user.setUserId(i);
            users.add(user);
        }
        userService.batchInsert(users);
        log.info("数据批量入库成功！");
    }
}
