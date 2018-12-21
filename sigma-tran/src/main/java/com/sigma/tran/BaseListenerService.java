package com.sigma.tran;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/26-17:52
 * desc: 監聽服務
 * 加上頭： @RabbitListener(queues = "queueName")
 **/
@Configuration
public abstract class BaseListenerService<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseListenerService.class);


    protected abstract MessageExchange getMessageExchange();

    /**
     * 设置交换机类型
     */
    @Bean
    public DirectExchange defaultExchange() {
        /**
         * DirectExchange:按照routingkey分发到指定队列
         * TopicExchange:多关键字匹配
         * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
         * HeadersExchange ：通过添加属性key-value匹配
         */
        return new DirectExchange(getMessageExchange().getExchange());
    }

    @Bean
    public Queue fooQueue() {
        return new Queue(getMessageExchange().getRoutingKey());
    }

    @Bean
    public Binding binding() {
        /** 将队列绑定到交换机 */
        return BindingBuilder.bind(fooQueue()).to(defaultExchange()).with(getMessageExchange().getRoutingKey());
    }

    @RabbitHandler
    public abstract void process(@Payload T foo);
}