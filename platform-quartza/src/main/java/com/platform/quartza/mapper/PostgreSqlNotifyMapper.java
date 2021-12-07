package com.platform.quartza.mapper;

import com.platform.quartza.base.repo.BaseMapper;
import com.platform.quartza.entity.BasePostgreResponse;

/**
 * @author: Advance
 * @create: 2021-12-04 10:29
 * @since V1.0.0
 */
public interface PostgreSqlNotifyMapper extends BaseMapper<BasePostgreResponse> {

    /**
     * pg通知插入测试
     * @author Advance
     * @date 2021/12/4 10:34
     * @return com.platform.quartza.entity.BasePostgreResponse
     */
    BasePostgreResponse pgNotifyTest();
}
