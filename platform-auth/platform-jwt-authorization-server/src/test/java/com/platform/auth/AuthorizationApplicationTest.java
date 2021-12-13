package com.platform.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.platform.common.util.JsonAdaptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Advance
 * @date 2021年12月10日 14:53
 * @since V1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthorizationApplication.class)
public class AuthorizationApplicationTest {
    @Autowired
    public JsonAdaptor jsonAdaptor;


    @Test
    public void contextLoads() {
    }


    public void printResult(Object obj) {
        if (obj != null) {
            String str = "";
            try {
                str = jsonAdaptor.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                System.out.println("json解析错误");
            }
            System.out.println("测试结果：" + str);
        } else {
            System.out.println("测试结果为空");
        }
    }
}
