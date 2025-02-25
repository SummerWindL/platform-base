package com.platform.es.demo.db;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年02月24日 14:10
 */
public class SubTableTimePreciseAlgorithm implements PreciseShardingAlgorithm<Long> {
    /**
     * @param availableTargetNames 所有的分片集 由于我这个算法是指定了分表算法，则这里是逻辑表名列表 目前则为"vehicle_alarm"
     * @param shardingValue 分片键（指定的那列作为分片条件）
     * @return
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        return shardingValue.getLogicTableName() + "_" + toDate(shardingValue.getValue());
    }

    public static String toDate(Long dateTimeMillSec) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        return sdf.format(new Date(dateTimeMillSec));
    }
}

