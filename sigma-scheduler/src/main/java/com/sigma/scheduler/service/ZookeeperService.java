package com.sigma.scheduler.service;

import com.sigma.scheduler.zookeeper.ZkPath;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/26-19:13
 * desc: Zk 服務
 **/
public interface ZookeeperService {
    /**
     * 掛起
     *
     * @param zkPath
     */
    void resume(ZkPath zkPath);

    /**
     * 暫停
     *
     * @param zkPath 路徑
     */
    void pause(ZkPath zkPath);

    /**
     * 運行
     *
     * @param zkPath 路徑
     */
    void run(ZkPath zkPath);

    /**
     * 取消
     *
     * @param zkPath 路徑
     */
    void cancel(ZkPath zkPath);

    /**
     * cron更新
     *
     * @param zkPath 路徑
     */
    void cronUpdate(ZkPath zkPath);
}
