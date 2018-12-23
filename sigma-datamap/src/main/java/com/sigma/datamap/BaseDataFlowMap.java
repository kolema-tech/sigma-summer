package com.sigma.datamap;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018-06-
 * desc:
 **/
@Slf4j
public abstract class BaseDataFlowMap<Input, Output> implements DataFlowMap<Input, Output> {

    @Override
    public DataFlowMap<Input, Output> map(Input input, Output output, Context context) throws Exception {

        DataMapMessage<Input, Output> message = null;
        if (context.isRebuild()) {
            message = context.read();
            output = message.getResponse();
        } else {
            output = execute(input);
        }

        context.setInput(input);
        context.setOutput(output);

        return this;
    }

    protected abstract Output execute(Input input);
}
