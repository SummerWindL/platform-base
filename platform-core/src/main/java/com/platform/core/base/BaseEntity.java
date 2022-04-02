package com.platform.core.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础入库对象
 * @author Advance
 * @date 2022年01月18日 14:34
 * @since V1.0.0
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -3527963820930472529L;
    private String status;
    private String createUser;
    private Date createTime;
    private String updateUser;
    private Date updateTime;
    private String createDept;

}
