package com.platform.quartza.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Advance
 * @date 2021年12月03日 17:01
 * @since V1.0.0
 */
@Table(name = "tbl_user")
@Data
@Setter
@Getter
public class User {
    @Id
    private Long userId;
    private String userName;
    private Integer userAge;
}
