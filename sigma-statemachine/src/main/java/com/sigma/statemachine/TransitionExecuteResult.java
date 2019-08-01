package com.sigma.statemachine;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/12-10:57
 * desc: 轉換執行結果
 **/
public enum TransitionExecuteResult {
    /**
     * 幂等
     */
    IDEM,

    /**
     * 需要请求
     */
    REQUEST,

    /**
     * 错误
     */
    ERROR;
}
