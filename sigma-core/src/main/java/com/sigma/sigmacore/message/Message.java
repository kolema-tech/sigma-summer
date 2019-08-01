package com.sigma.sigmacore.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huston.peng
 * @version 1.0.6
 * date-time: 2019/7/18-13:33
 * desc: 消息对象
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message<T extends MessageBody> {

    /**
     * 消息类别
     */
    private String msgType;

    /**
     * 消息体
     */
    private T body;

    /**
     * 创建消息
     *
     * @param messageType 消息类型
     * @param body        消息体
     * @return 返回一个消息
     */
    public static Message createMessage(String messageType, MessageBody body) {
        return new Message<>(messageType, body);
    }
}
