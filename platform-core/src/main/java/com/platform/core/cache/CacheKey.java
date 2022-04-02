package com.platform.core.cache;

/**
 * @author Advance
 * @date 2022年03月08日 16:52
 * @since V1.0.0
 */
public interface CacheKey {
    /**
     * 系统缓存
     */
    String APP_CACHE = "appCache";

    /**
     * 画面缓存
     */
    String PAGE_CACHE = "pageCache";

    /**
     * 菜单缓存
     */
    String MENU_CACHE = "menuCache";

    /**
     * 数据字典缓存
     */
    String DICT_CACHE = "dictCache";

    /**
     * 密码尝试次数缓存
     */
    String PASSWORD_RETRY_CACHE = "passwordRetryCache";

    /**
     * 密码尝试次数缓存
     */
    String USER_LOCK_CACHE = "userLockCache";
}
