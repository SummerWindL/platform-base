package com.platform.core.rabbitmq.entity;

import com.platform.core.util.SysCodeUtil;

import java.io.Serializable;

/**
 * @author Advance
 * @date 2022年07月17日 11:49
 * @since V1.0.0
 */
public interface IRabbitMessage extends Serializable {
    void setId(String var1);

    String getId();

    void setMessage(Serializable var1);

    Serializable getMessage();

    //void setResultMessage(Serializable result);

    //Serializable getResultMessage();

    default String getSysCode() {
        return SysCodeUtil.getSysCode();
    }

//    default String getUser() {
//        return LoginUserUtil.getUserName();
//    }
}
