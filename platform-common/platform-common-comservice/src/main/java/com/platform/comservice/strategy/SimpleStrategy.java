package com.platform.comservice.strategy;

import com.platform.comservice.annotation.StrategyFlag;
import com.platform.comservice.annotation.StrategyHandler;

/**
 * @author Advance
 * @date 2022年03月09日 13:51
 * @since V1.0.0
 */
@StrategyFlag("simple")
public class SimpleStrategy {
    @StrategyHandler
    public void handleSimple(){
        System.out.printf("simple strategy!");
    }
}
