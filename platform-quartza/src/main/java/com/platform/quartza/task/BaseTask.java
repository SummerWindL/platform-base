package com.platform.quartza.task;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.Page;
import com.platform.common.util.JSONUtil;
import com.platform.quartza.entity.BasePostgreResponse;
import com.platform.quartza.entity.User;
import com.platform.quartza.service.notify.PostgreSqlNotifyService;
import com.platform.quartza.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Advance
 * @date 2021年12月04日 10:18
 * @since V1.0.0
 */
@Component
public class BaseTask {
    private  final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final long SECOND = 1000;

    /**
     * 注入service
     */
    @Autowired
    private UserService userService;

    @Resource
    private PostgreSqlNotifyService postgreSqlNotifyService;

    /**
     * 固定间隔3秒，可以引用变量
     * fixedRate：以每次开始时间作为测量，间隔固定时间
     */
    //@Scheduled(fixedRate = 3 * SECOND)
    public void task1() {
        LOGGER.info("当前时间：{}\t\t任务：fixedRate task，每3秒执行一次", System.currentTimeMillis());
        Page<User> users = userService.getUsers();
        LOGGER.info("users：{}", JSONArray.toJSONString(users));
    }

    /**
     * 固定延迟3秒，从前一次任务结束开始计算，延迟3秒执行
     */
    //@Scheduled(fixedDelay = 3000)
    public void task3(){
        //do something
    }

    /**
     * cron表达式，每5秒执行
     */
    //@Scheduled(cron = "*/5 * * * * ?")
    public void task2() {
        LOGGER.info("当前时间：{}\t\t任务：cron task，每5秒执行一次", System.currentTimeMillis());
    }


    @Scheduled(fixedRate = 3 * SECOND)
    public void test3() {
        BasePostgreResponse basePostgreResponse = postgreSqlNotifyService.pgNotifyTest();

//        BasePostgreResponse basePostgreResponse = postgreSqlNotifyService.jdbcPgNofifyTest();
        LOGGER.info("响应：{}",basePostgreResponse);
    }
}
