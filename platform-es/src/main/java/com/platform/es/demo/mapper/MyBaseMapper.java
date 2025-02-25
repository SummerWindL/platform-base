package com.platform.es.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年02月24日 11:40
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {
    /**
     * 以下定义的 4个 method 其中 3 个是内置的选装件
     */
    int insertBatchSomeColumn(List<T> entityList);
}

