package com.platform.core.dict.entity;

import com.platform.core.annotation.PrimaryKey;
import com.platform.core.annotation.PrimaryKey.Field;
import com.platform.core.interf.DataDictItem;

import java.util.Map;
import java.util.Set;

/**
 * @author Advance
 * @date 2022年06月21日 19:24
 * @since V1.0.0
 */
public class SysDataDictItem implements DataDictItem {

    private static final long serialVersionUID = -3350576936978012714L;

    private String dictCode;

    private String itemCode;

    private String itemValue;

    private String orderNo;

    private String defaultValue;

    private String parentId;

    private Set<String> pageIds;

    /*
     *  所属机构
     */
    private String institutionCode;

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode == null ? null : institutionCode.trim();
    }

    /**
     * 对于画面的数据字典存储关系
     */
    private Map<String,SysPageDictlist> pageInfo;
    public SysDataDictItem() {
        super();
    }

    public SysDataDictItem(String dictCode, String itemCode, String itemValue, String orderNo, String defaultValue, String parentId, Set<String> pageIds,Map<String,SysPageDictlist> pageInfo) {
        super();
        this.dictCode = dictCode;
        this.itemCode = itemCode;
        this.itemValue = itemValue;
        this.orderNo = orderNo;
        this.defaultValue = defaultValue;
        this.parentId = parentId;
        this.pageIds = pageIds;
        this.pageInfo = pageInfo;
    }
    @Override
    @PrimaryKey(value=Field.PK_1)
    public String getDictCode() {
        return dictCode;
    }

    @Override
    @PrimaryKey(value=Field.PK_2)
    public String getItemCode() {
        return itemCode;
    }

    @Override
    public String getItemValue() {
        return itemValue;
    }

    @Override
    public String getOrderNo() {
        return orderNo;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public Set<String> getPageIds() {
        return pageIds;
    }

    public void setPageIds(Set<String> pageIds) {
        this.pageIds = pageIds;
    }


    /**
     * 获取 pageInfo
     * @return pageInfo.
     */
    public Map<String, SysPageDictlist> getPageInfo() {
        return pageInfo;
    }


    /**
     * 设置 pageInfo
     * @param pageInfo pageInfo
     */
    public void setPageInfo(Map<String, SysPageDictlist> pageInfo) {
        this.pageInfo = pageInfo;
    }

}

