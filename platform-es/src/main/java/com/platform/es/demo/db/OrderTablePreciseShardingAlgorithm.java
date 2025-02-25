package com.platform.es.demo.db;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年02月24日 15:53
 */
@Slf4j
public class OrderTablePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    public OrderTablePreciseShardingAlgorithm() {
    }

    @Override
    public String doSharding(Collection<String> tableCollection, PreciseShardingValue<Long> preciseShardingValue) {
        // 获取分片键的值
        Long shardingValue = preciseShardingValue.getValue();
        // 取模分表(取模都是从0到collection.size())
        long index = shardingValue % tableCollection.size();
        // 判断逻辑表名
        String logicTableName = preciseShardingValue.getLogicTableName();
        // 物理表名
        String PhysicalTableName = logicTableName + "_" + (index);

        log.info("分片键的值:{},物理表名:{}", shardingValue, PhysicalTableName);
        // 判断是否存在该表
        if (tableCollection.contains(PhysicalTableName)) {
            return PhysicalTableName;
        }
        // 不存在则抛出异常
        throw new UnsupportedOperationException();
    }
}

