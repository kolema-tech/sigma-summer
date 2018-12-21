package com.sigma.tran;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "CONFIRM_TEST")
public class FooListener extends BaseListenerService<String> {

    private MessageExchange messageExchange = null;

    @Override
    protected MessageExchange getMessageExchange() {
        if (messageExchange == null) {
            messageExchange = new MessageExchange("DIRECT_EX", "CONFIRM_TEST");
        }
        return messageExchange;
    }

    @Override
    public void process(String foo) {
        System.out.println("daniu:" + foo);
    }
}
