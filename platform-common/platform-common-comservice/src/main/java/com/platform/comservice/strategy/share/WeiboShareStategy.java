package com.platform.comservice.strategy.share;

import com.platform.comservice.annotation.StrategyFlag;
import com.platform.comservice.annotation.StrategyHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 微博分享策略
 * @author Advance
 * @date 2022年03月10日 15:18
 * @since V1.0.0
 */
@Slf4j
@StrategyFlag("Weibo")
public class WeiboShareStategy {

    @StrategyHandler
    public boolean doShares(){
        log.info("微博分享成功！");
        return true;
    }
}
