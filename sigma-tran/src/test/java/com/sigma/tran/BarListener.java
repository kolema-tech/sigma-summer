package com.sigma.tran;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "BAR")
public class BarListener extends BaseListenerService<String> {

    private MessageExchange messageExchange = null;

    @Override
    protected MessageExchange getMessageExchange() {
        if (messageExchange == null) {
            messageExchange = new MessageExchange("DIRECT_EX", "BAR");
        }
        return messageExchange;
    }

    @Override
    public void process(String foo) {
        System.out.println("daniu2:" + foo);
    }
}
