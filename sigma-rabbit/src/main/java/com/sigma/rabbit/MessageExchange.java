package com.sigma.rabbit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/12-10:10
 * desc: Broker交換
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageExchange {

    /**
     * exchange名称
     */
    private String exchange;

    /**
     * routingKey
     */
    private String routingKey;

    public static MessageExchange create(String exchange, String routingKey) {
        return new MessageExchange(exchange, routingKey);
    }
}
