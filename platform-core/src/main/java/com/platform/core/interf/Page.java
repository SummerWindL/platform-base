package com.platform.core.interf;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author Advance
 * @date 2022年06月21日 19:35
 * @since V1.0.0
 */
public interface Page extends Serializable {
    /**
     * 获取画面ID
     * @return 画面ID
     */
    String getId();

    /**
     * 获取画面名称
     * @return 画面名称
     */
    String getName();

    /**
     * 获取画面路径
     * @return 画面路径
     */
    String getPath();

    /**
     * 获取菜单ID
     * @return 菜单ID
     */
    String getMenuId();

    /**
     * 获取画面文件
     * @return 画面文件
     */
    String getFile();

    /**
     * 获取最小要求高度
     * @return 最小要求高度
     */
    String getHeight();
    /**
     * 获取最小要求宽度
     * @return 最小要求宽度
     */
    String getWidth();

    /**
     * 获取是否是主页配置可选对象
     * @return 是否是主页配置可选对象
     */
    String getIsHpSelect();

    /**
     * 获取是否是工作流程
     * @return 结果
     */
    String getIsNeedWorkflow();
    /**
     * 获取系统code
     * @return code
     */
    String getSystemCode();

    /**
     * 获取 isNeedSetEditModelWhenDraft
     * @return isNeedSetEditModelWhenDraft.
     */
    String getIsNeedSetEditModelWhenDraft();

    /**
     * 获取 isDraftNotTargetPage
     * @return isDraftNotTargetPage.
     */
    String getIsDraftNotTargetPage();
}

