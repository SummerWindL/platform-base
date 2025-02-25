package com.platform.es.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年02月24日 11:42
 */
public interface IMyService <T> extends IService<T> {
    int insertBatchSomeColumn(List<T> entityList);
    int insertBatchSomeColumn(List<T> entityList,int batchSize);
}
