package com.sigma.scheduler.sample;

import com.sigma.scheduler.CronJobConfig;
import com.sigma.scheduler.schedule.BaseSigmaJob;
import com.sigma.scheduler.service.ServiceManager;
import com.sigma.zookeeper.annotation.SLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/26-19:09
 * desc: 示例Job
 **/
@Component
public class MyJobSigmaJob extends BaseSigmaJob {

    @Autowired
    ServiceManager serviceManager;

    @SLock
    @CronJobConfig(expression = "0/3 * * * * ?")
    public void execute() {
        System.out.println(LocalDateTime.now() + " 定时任务,每3秒执行一次----------------" + serviceManager.getClass().getName());
    }
}

