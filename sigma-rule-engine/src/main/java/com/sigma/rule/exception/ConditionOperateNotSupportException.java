package com.sigma.rule.exception;

import com.sigma.sigmacore.exception.SigmaException;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/26-14:27
 * desc: 條件操作不支持
 **/
public class ConditionOperateNotSupportException extends SigmaException {
    public ConditionOperateNotSupportException(String message) {
        super(message);
    }
}
