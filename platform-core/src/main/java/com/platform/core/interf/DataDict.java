package com.platform.core.interf;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author Advance
 * @date 2022年06月21日 19:22
 * @since V1.0.0
 */
public interface DataDict extends Serializable {

    /**
     * 获取 dictCode
     *
     * @return dictCode.
     */
    String getDictCode();

    /**
     * 获取数据字典中文名
     *
     * @return 数据字典中文名.
     */
    String getDictCnname();

    /**
     * 获取数据字典英文名
     *
     * @return 数据字典英文名.
     */
    String getDictEnname();

    /**
     * 获取可更新标识
     *
     * @return 更新标识.
     */
    String getUpdateFlg();

    /**
     * 获取明细
     *
     * @return 数据字典明细.
     */
    List<? extends DataDictItem> getItems();

    /**
     * 获取数据字典对应的画面
     * @return 画面ID.
     */
    Set<String> getPageIds();

}

