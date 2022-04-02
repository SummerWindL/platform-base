package com.platform.comservice.config;

import com.platform.comservice.annotation.StrategyFlag;
import com.platform.comservice.annotation.StrategyHandler;
import com.platform.comservice.strategy.StrategyRunner;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 策略模式配置
 * @author Advance
 * @date 2022年03月09日 10:07
 * @since V1.0.0
 */
@Component
public class StrategyConfig implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Bean
    public StrategyRunner strategyRunner() {
        // 从容器中获取所有标注了StrategyFlag注解的类
        Map<String, Object> strategyClass = applicationContext.getBeansWithAnnotation(StrategyFlag.class);

        // 策略处理函数
        Map<String, Function<Void, Void>> strategyHandlers = new HashMap<>();

        // 遍历所有策略类
        for (Object s : strategyClass.values()) {
            // 获取策略标识
            String flag = s.getClass().getAnnotation(StrategyFlag.class).value();

            // 遍历策略类中的所有方法
            for (Method m : s.getClass().getMethods()) {
                // 如果方法标注了StrategyHandler注解，则封装成可调用对象加入strategyHandlers
                if (m.isAnnotationPresent(StrategyHandler.class)) {
                    strategyHandlers.put(flag, ignored -> {
                        try {
                            m.invoke(s);
                            return null;
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                    break;
                }
            }
        }

        return flag -> strategyHandlers.get(flag).apply(null);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 获取ApplicationContext容器对象
        this.applicationContext = applicationContext;
    }
}
