package com.platform.comservice.strategy;

import com.platform.comservice.PlatformComServiceApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 策略模式简单实现
 * @author Advance
 * @date 2022年03月09日 11:35
 * @since V1.0.0
 */
public class SimpleStrategyDemo extends PlatformComServiceApplicationTests {
    @Autowired
    private StrategyRunner strategyRunner;

    @Test
    public void test(){
        strategyRunner.run("default");
        strategyRunner.run("simple");

        strategyRunner.run("WeChat");
        strategyRunner.run("Weibo");
    }
}
