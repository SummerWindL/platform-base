package com.platform.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author Advance
 * @date 2021年12月10日 15:17
 * @since V1.0.0
 */
@Data
public class SysRole implements GrantedAuthority {
    private Integer id;
    private String roleName;
    private String roleDesc;
    /**
     * 如果授予的权限可以当作一个String的话，就可以返回一个String
     * @return
     */
    @JsonIgnore
    @Override
    public String getAuthority() {
        return roleName;
    }
}
