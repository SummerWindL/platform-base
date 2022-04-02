package com.platform.core.base.entity;

import lombok.Data;

/**
 * @author Advance
 * @date 2022年03月09日 9:15
 * @since V1.0.0
 */
@Data
public class CommonCheckResult<T> {
    private String msg;
    private Object obj;
}
