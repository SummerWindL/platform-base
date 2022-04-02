package com.platform.quartza.task;

import com.gyx.superscheduled.core.SuperScheduledManager;
import com.platform.quartza.PlatformQuartzaApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Advance
 * @date 2022年01月07日 15:34
 * @since V1.0.0
 */
public class TaskTest extends PlatformQuartzaApplicationTest {
    //直接注入管理器
    @Autowired
    private SuperScheduledManager superScheduledManager;

    @Test
    public void test() {
        //获取所有定时任务
        List<String> allSuperScheduledName = superScheduledManager.getAllSuperScheduledName();
        String name = allSuperScheduledName.get(0);
        //终止定时任务
        superScheduledManager.cancelScheduled(name);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("任务名：" + name);
        //启动定时任务
        superScheduledManager.addCronScheduled(name, "0/2 * * * * ?");
        //获取启动汇总的定时任务
        List<String> runScheduledName = superScheduledManager.getRunScheduledName();
        runScheduledName.forEach(System.out::println);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //修改定时任务执行周期
        superScheduledManager.setScheduledCron(name, "0/5 * * * * ?");
    }
}
