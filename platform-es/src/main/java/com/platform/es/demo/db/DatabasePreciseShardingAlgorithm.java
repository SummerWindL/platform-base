package com.platform.es.demo.db;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 分库算法
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年02月24日 15:48
 */
@Slf4j
public class DatabasePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {

    public DatabasePreciseShardingAlgorithm() {
    }

    @Override
    public String doSharding(Collection<String> dataSourceCollection, PreciseShardingValue<Integer> preciseShardingValue) {
        // 获取分片键的值
        Integer shardingValue = preciseShardingValue.getValue();
        // 获取逻辑
        String logicTableName = preciseShardingValue.getLogicTableName();
        log.info("分片键的值:{},逻辑表:{}", shardingValue, logicTableName);

        // 对分片键的值对10取模，得到（0-9）,我这里就配置了三个库，实际根据需要修改
        // 0,8插入到 ds1
        // 1,3,6,9插入到 ds2
        // 2,4,5,7插入到 ds3
        int index = shardingValue % 10;
        int sourceTarget = 0;
        if (ArrayUtils.contains(new int[]{0, 8, 2, 4, 6}, index)) {
            sourceTarget = 0;
        } else if (ArrayUtils.contains(new int[]{1, 3, 5,7, 9}, index)) {
            sourceTarget = 1;
        }

        // 遍历数据源
        for (String databaseSource : dataSourceCollection) {
            // 判断数据源是否存在
            if (databaseSource.endsWith(sourceTarget + "")) {
                return databaseSource;
            }
        }
        // 不存在则抛出异常
        throw new UnsupportedOperationException();
    }
}

