package com.platform.es;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.platform.common.util.JsonAdaptor;
import com.platform.es.demo.entity.User;
import com.platform.es.demo.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年02月21日 18:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlatformEsApplication.class)
@MapperScan("com.platform.es.demo.mapper")
public class PlatformEsApplicationTest {
    @Resource(name = "jsonAdaptor")
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
