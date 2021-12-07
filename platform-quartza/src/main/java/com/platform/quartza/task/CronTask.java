package com.platform.quartza.task;

import com.platform.common.util.DateTimeUtil;
import lombok.extern.log4j.Log4j2;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * cron 表达式任务
 * @author Advance
 * @date 2021年12月03日 16:15
 * @since V1.0.0
 */
public class CronTask extends QuartzJobBean {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("CronTask----{}", DateTimeUtil.formatDatetime(new Date()));
    }
}
