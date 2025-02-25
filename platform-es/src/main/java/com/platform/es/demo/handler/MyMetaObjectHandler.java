package com.platform.es.demo.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年02月21日 18:34
 */
//自动填充实现类
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    //mybatis-plus实现添加操作
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("version",1,metaObject);
    }

    //mybatis-plus实现更新操作
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}

