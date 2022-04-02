package com.platform.quartza.controller.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gyx.superscheduled.core.SuperScheduledManager;
import com.platform.common.util.AssertUtil;
import com.platform.common.util.JsonAdaptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Advance
 * @date 2022年01月08日 10:17
 * @since V1.0.0
 */
@Slf4j
@RestController
public class TaskManagerController {
    @Resource
    private SuperScheduledManager superScheduledManager;
    @Resource
    private JsonAdaptor jsonAdaptor;

    /**
     * 获取所有任务
     * @author Advance
     * @date 2022/1/8 11:00
     * @return java.lang.String
     */
    @GetMapping("/tasks/getAllTask")
    private String getAllScheduleTaskList(){
        List<String> allSuperScheduledName = superScheduledManager.getAllSuperScheduledName();
        try {
            return jsonAdaptor.writeValueAsString(allSuperScheduledName);
        } catch (JsonProcessingException e) {
            log.error("json解析错误！",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取所有启动的定时任务
     * @author Advance
     * @date 2022/1/8 11:29
     * @return java.lang.String
     */
    @GetMapping("/tasks/getRunTask")
    private String getAllRunScheduleTaskList(){
        List<String> runScheduledName = superScheduledManager.getRunScheduledName();
        try {
            return jsonAdaptor.writeValueAsString(runScheduledName);
        } catch (JsonProcessingException e) {
            log.error("json解析错误！",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 终止任务
     * @author Advance
     * @date 2022/1/8 11:32
     * @param taskName 
     */
    @GetMapping("/tasks/stopTask/{taskName}")
    private void stopScheduleTask(@PathVariable("taskName") String taskName){
        log.info("需要终止的任务名称：{} ",taskName);
        AssertUtil.notNull(taskName,"任务名称为空！");
        if(superScheduledManager.isScheduledExists(taskName)) {
            superScheduledManager.cancelScheduled(taskName);
        }
    }

}
