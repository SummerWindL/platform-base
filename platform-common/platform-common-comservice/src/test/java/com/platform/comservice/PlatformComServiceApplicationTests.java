package com.platform.comservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.platform.common.util.JsonAdaptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName PlatformComServiceApplicationTest
 * @Description
 * @Author yanl
 * @Date 2020/9/12 0:44
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class PlatformComServiceApplicationTests {
    @Autowired
    protected JsonAdaptor jsonAdaptor;


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
