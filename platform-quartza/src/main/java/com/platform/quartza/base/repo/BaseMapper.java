package com.platform.quartza.base.repo;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author: Advance
 * @create: 2021-12-03 16:57
 * @since V1.0.0
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
