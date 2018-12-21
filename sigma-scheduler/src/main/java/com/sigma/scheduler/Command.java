package com.sigma.scheduler;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/12-13:51
 * desc: 命令
 **/
public enum Command {

    /**
     * 回復
     */
    RESUME,

    /**
     * 暫停
     */
    PAUSE,

    /**
     * 立即運行
     */
    RUN,

    /**
     * 取消
     */
    CANCEL,

    /**
     * 修改表達式
     */
    CRON;
}
