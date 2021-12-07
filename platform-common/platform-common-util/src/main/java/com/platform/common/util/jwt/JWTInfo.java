package com.platform.common.util.jwt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName        : JWTInfo
 * @author            : Advance
 * @date : 2017年12月21日 下午3:05:19
 */
@ToString
@Setter
@Getter
public class JWTInfo implements Serializable {

    private static final long serialVersionUID = -525924043776022944L;

    private String id;

    private String name;

    private String alias;

    private String role;

    public JWTInfo(String id, String name, String alias) {
        this.id = id;
        this.name = name;
        this.alias = alias;
    }

    public JWTInfo(String id, String name, String alias, String role) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JWTInfo jwtInfo = (JWTInfo) o;

        if (name != null ? !name.equals(jwtInfo.name) : jwtInfo.name != null) {
            return false;
        }
        return id != null ? id.equals(jwtInfo.id) : jwtInfo.id == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
