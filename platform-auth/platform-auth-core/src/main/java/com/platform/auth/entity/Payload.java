package com.platform.auth.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Advance
 * @date 2021年12月10日 14:40
 * @since V1.0.0
 * 为了方便后期获取token中的用户信息，将token中载荷部分单独封装成一个对象
 */
@Data
public class Payload<T> {
    private String id;
    private T userInfo;
    private Date expiration;
}
