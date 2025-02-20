package com.platform.core.dict.entity;

import com.platform.core.base.BaseEntity;

/**
 * @author Advance
 * @date 2022年06月21日 19:28
 * @since V1.0.0
 */
public class SysPageDictlistKey extends BaseEntity {
    /**
     * 画面代码（菜单对应的入口画面）
     */
    private String pageId;

    /**
     * 数据字典代码
     */
    private String dictId;

    /**
     * 数据字典明细值
     */
    private String dictItemVal;

    /**
     * 子画面代码
     */
    private String subPageId;

    /**
     * 取得 画面代码（菜单对应的入口画面）
     * @return 画面代码（菜单对应的入口画面）
     */
    public String getPageId() {
        return pageId;
    }

    /**
     * 设置 画面代码（菜单对应的入口画面）
     * @param pageId 画面代码（菜单对应的入口画面）
     */
    public void setPageId(String pageId) {
        this.pageId = pageId == null ? null : pageId.trim();
    }

    /**
     * 取得 数据字典代码
     * @return 数据字典代码
     */
    public String getDictId() {
        return dictId;
    }

    /**
     * 设置 数据字典代码
     * @param dictId 数据字典代码
     */
    public void setDictId(String dictId) {
        this.dictId = dictId == null ? null : dictId.trim();
    }

    /**
     * 取得 数据字典明细值
     * @return 数据字典明细值
     */
    public String getDictItemVal() {
        return dictItemVal;
    }

    /**
     * 设置 数据字典明细值
     * @param dictItemVal 数据字典明细值
     */
    public void setDictItemVal(String dictItemVal) {
        this.dictItemVal = dictItemVal == null ? null : dictItemVal.trim();
    }

    /**
     * 取得 子画面代码
     * @return 子画面代码
     */
    public String getSubPageId() {
        return subPageId;
    }

    /**
     * 设置 子画面代码
     * @param subPageId 子画面代码
     */
    public void setSubPageId(String subPageId) {
        this.subPageId = subPageId == null ? null : subPageId.trim();
    }
}