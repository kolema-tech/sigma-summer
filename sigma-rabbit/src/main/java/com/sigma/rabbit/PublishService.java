package com.sigma.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/12-10:16
 * desc: 默認的發送服務
 **/
@Service
@Slf4j
public class PublishService implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public PublishService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;

        this.rabbitTemplate.setConfirmCallback(this);
        this.rabbitTemplate.setReturnCallback(this);
    }

    public void send(String exchange, String routingKey, Object object) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        send(exchange, routingKey, object, correlationData);
    }

    public void send(String exchange, String routingKey, Object object, CorrelationData correlationData) {
        rabbitTemplate.convertAndSend(exchange, routingKey, object, correlationData);
    }

    public Object sendAndReceive(String exchange, String routingKey, Object object, CorrelationData correlationData, long replyTimeout) {
        rabbitTemplate.setReplyTimeout(replyTimeout);
        return rabbitTemplate.convertSendAndReceive(exchange, routingKey, object, correlationData);
    }

    /**
     * 确认回调
     *
     * @param correlationData 关联数据
     * @param ack             ack值
     * @param cause           原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("confirm :correlationData:" + correlationData + ",ack:" + ack + ",cause:" + cause);
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("return：message:" + new String(message.getBody()) + ",replyCode:" + replyCode + ",replyText:" + replyText + ",exchange:" + exchange + ",routingKey:" + routingKey);
    }
}