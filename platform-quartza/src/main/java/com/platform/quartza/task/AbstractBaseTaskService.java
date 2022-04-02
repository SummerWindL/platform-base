package com.platform.quartza.task;

import com.gyx.superscheduled.core.SuperScheduledManager;
import com.platform.common.util.JsonAdaptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * base 定时任务基类
 * @author Advance
 * @date 2022年01月07日 16:13
 * @since V1.0.0
 */
@Component
@Slf4j
public class AbstractBaseTaskService implements CommandLineRunner {
    @Resource
    private JsonAdaptor jsonAdaptor;
    @Resource
    private SuperScheduledManager superScheduledManager;


    @Override
    public void run(String... args) throws Exception {
        List<String> allSuperScheduledName = superScheduledManager.getAllSuperScheduledName();
        log.info("allSuperScheduledName：{}",jsonAdaptor.writeValueAsString(allSuperScheduledName));
    }
}
