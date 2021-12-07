package com.platform.quartza.entity;

import lombok.Data;

/**
 * postgresql 存储过程返回实体
 * @author Advance
 * @date 2021年12月04日 10:32
 * @since V1.0.0
 */
@Data
public class BasePostgreResponse {
    /**
     * retcode
     */
    private int retcode;
    /**
     * retvalue
     */
    private String retvalue;
}
