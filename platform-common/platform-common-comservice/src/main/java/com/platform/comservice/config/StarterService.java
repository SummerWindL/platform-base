package com.platform.comservice.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.util.StringUtils;

/**
 * 切面定义
 * @author Advance
 * @date 2022年04月12日 10:25
 * @since V1.0.0
 */
public class StarterService {

    private  static BeanFactory beanFactory;

    public StarterService() {
    }

    private String userStr;

    public StarterService(String userStr) {
        this.userStr = userStr;
    }

    public String[] split(String separatorChar) {
        return StringUtils.split(this.userStr,separatorChar);
    }

}
