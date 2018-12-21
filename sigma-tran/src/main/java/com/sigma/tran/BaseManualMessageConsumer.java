package com.sigma.tran;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/7-10:22
 * desc: 手動確認消息模型
 **/
@Slf4j
public abstract class BaseManualMessageConsumer implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

        try {
            processMessage(message);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception ex) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

    public abstract void processMessage(Message message);
}
