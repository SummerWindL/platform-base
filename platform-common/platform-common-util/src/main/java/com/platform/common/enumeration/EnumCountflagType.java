package com.platform.common.enumeration;

/**
 * @ClassName EnumCountflagType
 * @Description: 1 查询记录总数和分页的记录，记录总数放在retvalue中；
 * 2 仅查询记录总数，不查询分页记录，记录总数放在retvalue中，
 * 3 仅查询分页记录，不查询记录总数。 retvalue始终=0
 * @Author yanl
 * @Date 2020/8/25 0:46
 * @Version 1.0
 **/
public enum EnumCountflagType {

    ALL(1),
    COUNT(2),
    LIST(3),
    NOPAGE(4);

    private Integer countflag;

    EnumCountflagType(Integer countflag) {
        this.countflag = countflag;
    }

    public Integer getCountflag() {
        return countflag;
    }

    public void setCountflag(Integer countflag) {
        this.countflag = countflag;
    }

    public static boolean isSupportType(Integer countflag) {
        for (EnumCountflagType enumType : values()) {
            if (enumType.getCountflag().equals(countflag)) {
                return true;
            }
        }
        return false;
    }
}
