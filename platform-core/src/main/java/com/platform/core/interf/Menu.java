package com.platform.core.interf;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author Advance
 * @date 2022年06月21日 19:35
 * @since V1.0.0
 */
public interface Menu extends Serializable {

    /**
     * ID
     * @return ID
     */
    String getId();

    /**
     * 获取图标
     * @return 图标
     */
    String getIcon();

    /**
     * 获取菜单名
     * @return 菜单名
     */
    String getName();

    /**
     * 获取父菜单
     * @return 父菜单
     */
    Menu getParent();

    /**
     * 获取画面
     * @return 画面
     */
    Page getPage();

    /**
     * 获取排序
     * @return 排序
     */
    int getOrderNo();

    /**
     * 是否展开
     * @return 是否展开
     */
    boolean isSpread();

    /**
     * 获取子菜单
     * @return 子菜单
     */
    Collection<Menu> getChildren();
}

