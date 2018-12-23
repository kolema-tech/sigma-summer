package com.sigma.datamap;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018-06-
 * desc: 数据流 map
 **/
public interface DataFlowMap<Input, Output> {

    DataFlowMap<Input,Output> map(Input input, Output output, Context context) throws Exception;
}
