package com.sigma.scheduler.event;

import com.sigma.scheduler.service.ZookeeperService;
import org.springframework.context.ApplicationListener;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/26-19:08
 * desc: 更新Cron處理器
 **/
public class CronUpdateHandler implements ApplicationListener<CronUpdateEvent> {

    ZookeeperService zookeeperService;

    @Override
    public void onApplicationEvent(CronUpdateEvent cronUpdateEvent) {
        CronUpdateData cronUpdateData = (CronUpdateData) cronUpdateEvent.getSource();
        zookeeperService.cronUpdate(cronUpdateData.getZkPath());
    }
}
