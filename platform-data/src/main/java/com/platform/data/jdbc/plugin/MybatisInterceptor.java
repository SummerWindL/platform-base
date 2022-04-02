package com.platform.data.jdbc.plugin;

import org.apache.ibatis.plugin.Interceptor;
import org.springframework.core.Ordered;

/**
 * @author: Advance
 * @create: 2022-01-18 14:26
 * @since V1.0.0
 */
public interface MybatisInterceptor extends Interceptor, Ordered {
}
