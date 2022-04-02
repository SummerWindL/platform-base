package com.platform.comservice.strategy.share;

import com.platform.comservice.annotation.StrategyFlag;
import com.platform.comservice.annotation.StrategyHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 微信朋友圈分项策略
 * @author Advance
 * @date 2022年03月10日 15:17
 * @since V1.0.0
 */
@Slf4j
@StrategyFlag("WeChat")
public class WeChatShareStrategy {
    @StrategyHandler
    public boolean doShares(){
        log.info("微信分享成功！");
        return true;
    }
}
