package com.platform.core.dict.entity;

import com.platform.core.interf.DataDict;

import java.util.List;
import java.util.Set;

/**
 * @author Advance
 * @date 2022年06月21日 19:24
 * @since V1.0.0
 */
public class SysDataDict implements DataDict {

    private static final long serialVersionUID = -3350576936978012712L;

    private String dictCode;

    private String dictCnname;

    private String dictEnname;

    private String updateFlg;

    private List<SysDataDictItem> items;

    private Set<String> pageIds;

    public SysDataDict() {
        super();
    }

    public SysDataDict(String dictCode, String dictCnname, String dictEnname, String updateFlg, List<SysDataDictItem> items, Set<String> pageIds) {
        super();
        this.dictCode = dictCode;
        this.dictCnname = dictCnname;
        this.dictEnname = dictEnname;
        this.updateFlg = updateFlg;
        this.items = items;
        this.pageIds = pageIds;
    }

    @Override
    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    @Override
    public String getDictCnname() {
        return dictCnname;
    }

    public void setDictCnname(String dictCnname) {
        this.dictCnname = dictCnname;
    }

    @Override
    public String getDictEnname() {
        return dictEnname;
    }

    public void setDictEnname(String dictEnname) {
        this.dictEnname = dictEnname;
    }

    @Override
    public String getUpdateFlg() {
        return updateFlg;
    }

    public void setUpdateFlg(String updateFlg) {
        this.updateFlg = updateFlg;
    }

    @Override
    public List<SysDataDictItem> getItems() {
        return items;
    }

    public void setItems(List<SysDataDictItem> items) {
        this.items = items;
    }

    @Override
    public Set<String> getPageIds() {
        return pageIds;
    }

    public void setPageIds(Set<String> pageIds) {
        this.pageIds = pageIds;
    }
}

