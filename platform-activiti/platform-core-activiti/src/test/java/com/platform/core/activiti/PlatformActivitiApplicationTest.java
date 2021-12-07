package com.platform.core.activiti;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.platform.common.util.JsonAdaptor;
import com.platform.core.activiti.PlatformActivitiApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Advance
 * @date 2021年12月06日 9:28
 * @since V1.0.0
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlatformActivitiApplication.class)
public class PlatformActivitiApplicationTest {
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
