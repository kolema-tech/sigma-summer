package com.sigma.scheduler.schedule;

import com.sigma.scheduler.service.ZookeeperService;
import com.sigma.scheduler.zookeeper.ZkPath;
import lombok.experimental.var;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/7-17:14
 * desc: Sigma 調度器
 **/
@Component
public class SigmaScheduler {

    @Autowired
    SigmaTriggerSchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    ZookeeperService zookeeperService;

    /**
     * 調度器
     */
    private Scheduler scheduler = null;

    public void resumeJob(String jobName, String jobGroup) throws SchedulerException {
        scheduler = schedulerFactoryBean.getScheduler();
        scheduler.resumeJob(JobKey.jobKey(jobName, jobGroup));

        zookeeperService.resume(ZkPath.createResume(jobName, false));
    }

    public void pauseJob(String jobName, String jobGroup) throws SchedulerException {
        scheduler = schedulerFactoryBean.getScheduler();
        scheduler.pauseJob(JobKey.jobKey(jobName, jobGroup));

        zookeeperService.pause(ZkPath.createPause(jobName, false));
    }

    public void cronUpdate(String jobName, String jobGroup, String cron) throws SchedulerException {

        scheduler = schedulerFactoryBean.getScheduler();

        var cronTrigger = TriggerBuilder
                .newTrigger()
                .withIdentity(jobName, jobGroup)
                .withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();

        scheduler.rescheduleJob(TriggerKey.triggerKey(jobName, jobGroup), cronTrigger);

        zookeeperService.cronUpdate(ZkPath.createCron(jobName, false, cron));
    }

    public void run(String jobName, String jobGroup) throws SchedulerException {
        var job = scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroup));
        if (job != null) {
            scheduler.triggerJob(JobKey.jobKey(jobName, jobGroup));

            zookeeperService.run(ZkPath.createRun(jobName, false));
        }
    }
}
