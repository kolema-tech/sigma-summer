package com.sigma.sigmacore;

/**
 * @author zen peng.
 * @version 1.0.3
 * date-time: 2018/12/8-16:41
 * desc:
 **/
@FunctionalInterface
public interface PrintLog {
    /**
     * 打印日志
     *
     * @param message 消息
     * @param detail 详细
     */
    void print(String message, String detail);
}
