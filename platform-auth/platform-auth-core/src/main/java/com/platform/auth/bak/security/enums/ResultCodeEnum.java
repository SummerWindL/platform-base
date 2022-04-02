package com.platform.auth.bak.security.enums;

/**
 * @description:
 * @author: fuyanliang
 * @create: 2021-12-09 16:26
 **/
public enum ResultCodeEnum {

    EXPIRED,
    SIGNATURE,
    ERROR,
    JWT_EXPIRED(ResultCodeEnum.EXPIRED,"token过期"),
    JWT_SIGNATURE(ResultCodeEnum.SIGNATURE,"签名错误"),
    JWT_ERROR(ResultCodeEnum.SIGNATURE,"token解析错误");

    ResultCodeEnum() {
    }

    ResultCodeEnum(ResultCodeEnum code, String message) {
        this.code = code;
        this.message = message;
    }

    ResultCodeEnum code;
    String message;
    public ResultCodeEnum code(){
        return code;
    }

    public String message(){
        return message;
    }
}
