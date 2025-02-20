package com.platform.core.dict.entity;

/**
 * @author Advance
 * @date 2022年06月21日 19:28
 * @since V1.0.0
 */
public class SysPageDictlist extends SysPageDictlistKey {
    /**
     * 数据字典明细中文名称
     */
    private String dictItemValCnname;

    /**
     * 数据字典明细默认值
     */
    private String dictItemValDefault;

    /**
     * 机构代码INSTITUTION_CODE
     */
    private String institutionCode;

    /**
     * 取得 数据字典明细中文名称
     * @return 数据字典明细中文名称
     */
    public String getDictItemValCnname() {
        return dictItemValCnname;
    }

    /**
     * 设置 数据字典明细中文名称
     * @param dictItemValCnname 数据字典明细中文名称
     */
    public void setDictItemValCnname(String dictItemValCnname) {
        this.dictItemValCnname = dictItemValCnname == null ? null : dictItemValCnname.trim();
    }

    /**
     * 取得 数据字典明细默认值
     * @return 数据字典明细默认值
     */
    public String getDictItemValDefault() {
        return dictItemValDefault;
    }

    /**
     * 设置 数据字典明细默认值
     * @param dictItemValDefault 数据字典明细默认值
     */
    public void setDictItemValDefault(String dictItemValDefault) {
        this.dictItemValDefault = dictItemValDefault == null ? null : dictItemValDefault.trim();
    }

    /**
     * 获取 institutionCode
     *
     * @return institutionCode
     */
    public String getInstitutionCode() {
        return institutionCode;
    }

    /**
     * 设置 institutionCode
     *
     * @param institutionCode institutionCode
     */
    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }
}