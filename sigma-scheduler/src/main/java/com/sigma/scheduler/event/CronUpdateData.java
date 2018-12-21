package com.sigma.scheduler.event;

import com.sigma.scheduler.zookeeper.ZkPath;
import lombok.Data;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/26-19:08
 * desc: 更新Cron數據
 **/
@Data
public class CronUpdateData {
    private ZkPath zkPath;
    private String cron;
}
