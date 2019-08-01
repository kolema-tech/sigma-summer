package com.sigma.statemachine;

import lombok.experimental.var;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/12-11:00
 * desc: 持久化狀態機服務
 **/
@Service
public class PersistStateMachineService {

    private static final String KEY = "BUS_KEY";
    private final PersistStateMachineHandler handler;
    private final PersistStateMachineHandler.PersistStateChangeListener listener = new LocalPersistStateChangeListener();
    private Boolean updated = false;

    /**
     * 构造器
     *
     * @param handler
     */
    public PersistStateMachineService(PersistStateMachineHandler handler) {
        this.handler = handler;
        this.handler.addPersistStateChangeListener(listener);
    }

    /**
     * 尝试转换
     *
     * @param event
     * @return
     */
    public TransitionExecuteResult tryMakeTransition(String key, String event, String state) {

        updated = false;

        var transitionSuccess = handler.handleEventWithState(
                MessageBuilder
                        .withPayload(event)
                        .setHeader(KEY, key)
                        .build(), state);

        if (transitionSuccess) {
            if (updated) {
                return TransitionExecuteResult.REQUEST;
            } else {
                return TransitionExecuteResult.IDEM;
            }
        }

        return TransitionExecuteResult.ERROR;
    }

    /**
     * 本地持久化监听器
     */
    private class LocalPersistStateChangeListener implements PersistStateMachineHandler.PersistStateChangeListener {

        /**
         * 持久化时发生
         *
         * @param state        the state
         * @param message      the message
         * @param transition   the transition
         * @param stateMachine the state machine
         */
        @Override
        public void onPersist(State<String, String> state,
                              Message<String> message,
                              Transition<String, String> transition,
                              StateMachine<String, String> stateMachine) {

            if (message != null && message.getHeaders().containsKey(KEY)) {
                //需要更新状态
                if (!transition.getSource().equals(transition.getTarget())) {
                    updated = true;
                }
            }
        }
    }
}
