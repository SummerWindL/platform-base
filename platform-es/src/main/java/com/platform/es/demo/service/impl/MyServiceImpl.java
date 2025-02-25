package com.platform.es.demo.service.impl;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年02月24日 11:44
 */
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.es.demo.mapper.MyBaseMapper;
import com.platform.es.demo.service.IMyService;


import java.util.ArrayList;
import java.util.List;

public class MyServiceImpl<M extends MyBaseMapper<T>, T>extends ServiceImpl<M,T> implements IMyService<T> {
    @Override
    public int insertBatchSomeColumn(List<T> entityList) {
        return this.baseMapper.insertBatchSomeColumn(entityList);
    }

    @Override
    public int insertBatchSomeColumn(List<T> entityList, int batchSize) {
        int size=entityList.size();
        if(size<batchSize){
            return this.baseMapper.insertBatchSomeColumn(entityList);
        }
        int page=1;
        if(size % batchSize ==0){
            page=size/batchSize;
        }else {
            page=size/batchSize+1;
        }
        for (int i = 0; i < page; i++) {
            List<T> sub = new ArrayList<>();
            if(i==page-1){
                sub=entityList.subList(i*batchSize, entityList.size());
            }else {
                sub.subList(i*batchSize,(i+1)*batchSize);
            }
            if(sub.size()>0){
                baseMapper.insertBatchSomeColumn(sub);
            }

        }
        return size;
    }
}

