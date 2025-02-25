package com.platform.es.demo.db;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.Objects;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年02月24日 14:14
 */
public class SubDataBasePreciseAlgorithm implements PreciseShardingAlgorithm<String> {
    /**
     * @param availableTargetNames 所有的分片集 由于我这个算法是指定了分表算法，则这里是库列表即names 指定的名字列表
     * @param shardingValue 分片键（指定的那列作为分片条件）
     * @return
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        for (String databaseName : availableTargetNames) {
            String zoneValue = shardingValue.getValue();
            zoneValue = zoneValue == null || Objects.equals(zoneValue.replace(" ", ""), "") ? "sc" : zoneValue;
            if (databaseName.endsWith(zoneValue)) {
                return databaseName;
            }
        }
        throw new IllegalArgumentException();
    }
}

