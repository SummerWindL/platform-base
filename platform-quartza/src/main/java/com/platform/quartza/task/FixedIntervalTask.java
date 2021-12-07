package com.platform.quartza.task;

import com.platform.common.util.DateTimeUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * 固定间隔任务
 * @author Advance
 * @date 2021年12月03日 16:15
 * @since V1.0.0
 */
public class FixedIntervalTask extends QuartzJobBean {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("FixedIntervalTask----{}",DateTimeUtil.formatDatetime(new Date()));
    }
}
