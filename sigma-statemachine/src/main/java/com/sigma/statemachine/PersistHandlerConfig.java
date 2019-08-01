package com.sigma.statemachine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;

/**
 * @author zhenpeng
 */
@Configuration
public class PersistHandlerConfig {

    @Bean
    public PersistStateMachineHandler persistStateMachineHandler(StateMachine<String, String> stateMachine) {
        return new PersistStateMachineHandler(stateMachine);
    }
}
