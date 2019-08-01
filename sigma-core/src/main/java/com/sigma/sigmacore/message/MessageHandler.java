package com.sigma.sigmacore.message;

/**
 * @author huston.peng
 * @version 1.0.6
 * date-time: 2019/7/18-13:35
 * desc:
 **/
public interface MessageHandler {

    /**
     * 处理消息
     *
     * @param message 消息
     */
    void handle(Message message);
}
