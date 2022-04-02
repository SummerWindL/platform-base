package com.platform.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;

/**
 * @author Advance
 * @date 2022年03月21日 14:35
 * @since V1.0.0
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    /**
     * Spring应用上下文环境
     */
    private static ApplicationContext APPLICATION_CONTEXT;

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     *
     * @param applicationContext 上下文
     * @throws BeansException 异常
     */
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringContextUtil.APPLICATION_CONTEXT = applicationContext;
    }

    /**
     * @return ApplicationContext返回给其它模块使用
     */
    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

    /**
     * 主要功能: 获取bean 注意事项:无
     *
     * @param arg0 参数
     * @return 返回Bean实例
     */
    public static Object getBean(String arg0) {
        return APPLICATION_CONTEXT.getBean(arg0);
    }

    /**
     * 主要功能:获取bean并转换对象 注意事项:无
     *
     * @param beanType bean类型
     * @param <T> 返回类型
     * @return 返回Bean实例
     */
    @SuppressWarnings("hiding")
    public static <T> T getBean(Class<T> beanType) {
        return APPLICATION_CONTEXT.getBean(beanType);
    }

    /**
     * 主要功能: 获取bean 注意事项:无
     *
     * @param beanType 参数
     * @param <T> 返回类型
     * @return 返回Bean实例
     */
    @SuppressWarnings("hiding")
    public static <T> Map<String, T> getBeans(Class<T> beanType) {
        return APPLICATION_CONTEXT.getBeansOfType(beanType);
    }

    public static Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }

    /**
     * 获取message
     *
     * @param key 消息定义key
     * @return i18n消息内容
     */
    public static String getTextValue(String key) {
        try {
            return APPLICATION_CONTEXT.getMessage(key, null, getLocale());
        }
        catch (Exception e) {
        }
        return key;
    }

    /**
     * 获取message
     *
     * @param key 消息定义key
     * @param args 参数
     * @return i18n消息内容
     */
    public static String getTextValue(String key, String... args) {
        try {
            return APPLICATION_CONTEXT.getMessage(key, args, getLocale());
        }
        catch (Exception e) {
        }
        return key;
    }

    /**
     * 获取message
     *
     * @param key 消息定义key
     * @param args 参数
     * @return i18n消息内容
     */
    public static String getTextValue(String key, Object[] args) {
        try {
            return APPLICATION_CONTEXT.getMessage(key, args, getLocale());
        }
        catch (Exception e) {
        }
        return key;
    }

    /**
     * 获取message
     *
     * @param key 消息定义key
     * @param args 参数
     * @param locale 地区
     * @return i18n消息内容
     */
    public static String getTextValue(String key, Object[] args, Locale locale) {
        try {
            return APPLICATION_CONTEXT.getMessage(key, args, locale);
        }
        catch (Exception e) {
        }
        return key;
    }

    /**
     * 获取message
     *
     * @param key 消息定义key
     * @return i18n消息内容
     */
    public static <T> T getPropValue(String key, Class<T> clz) {
        try {
            return APPLICATION_CONTEXT.getEnvironment().getProperty(key, clz);
        }
        catch (Exception e) {
        }
        return null;
    }
}