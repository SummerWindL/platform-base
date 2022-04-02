package com.platform.comservice.strategy;

import com.platform.comservice.annotation.StrategyFlag;
import com.platform.comservice.annotation.StrategyHandler;

/**
 * @author Advance
 * @date 2022年03月09日 10:03
 * @since V1.0.0
 */
@StrategyFlag("default")
public class DefaultStrategy {
    @StrategyHandler
    public void handleDefaultStrategy() {
        System.out.println("Default Strategy!");
    }
}
