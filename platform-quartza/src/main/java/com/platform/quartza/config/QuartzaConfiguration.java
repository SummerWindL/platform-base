package com.platform.quartza.config;

import com.platform.quartza.task.CronTask;
import com.platform.quartza.task.FixedIntervalTask;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * quartz 定时任务配置
 * @author Advance
 * @date 2021年12月03日 16:16
 * @since V1.0.0
 */
@Configuration
public class QuartzaConfiguration {

    @Bean
    public JobDetail testQuartz1() {
        return JobBuilder.newJob(FixedIntervalTask.class).withIdentity("fixedIntervalTask").storeDurably().build();
    }

    @Bean
    public Trigger testQuartzTrigger1() {
        //5秒执行一次
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(5)
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(testQuartz1())
                .withIdentity("fixedIntervalTask")
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public JobDetail testQuartz2() {
        return JobBuilder.newJob(CronTask.class).withIdentity("cronTask").storeDurably().build();
    }

    @Bean
    public Trigger testQuartzTrigger2() {
        //cron方式，每隔5秒执行一次
        return TriggerBuilder.newTrigger().forJob(testQuartz2())
                .withIdentity("cronTask")
                .withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?"))
                .build();
    }

}
