package com.platform.core.interf;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Advance
 * @date 2022年06月21日 19:23
 * @since V1.0.0
 */
public interface DataDictItem extends Serializable {

    /**
     * 获取 dictCode
     *
     * @return dictCode.
     */
    String getDictCode();

    /**
     * 获取 itemCode
     *
     * @return itemCode.
     */
    String getItemCode();

    /**
     * 获取 itemValue
     *
     * @return itemValue.
     */
    String getItemValue();

    /**
     * 获取 orderNo
     *
     * @return orderNo.
     */
    String getOrderNo();

    /**
     * 获取 defaultValue
     *
     * @return defaultValue.
     */
    String getDefaultValue();

    /**
     * 获取 parentId
     *
     * @return parentId.
     */
    String getParentId();

    /**
     * 获取 pageIds
     *
     * @return pageIds.
     */
    Set<String> getPageIds();

    String getInstitutionCode();

}

