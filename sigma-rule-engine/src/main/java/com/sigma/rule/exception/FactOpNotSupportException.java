package com.sigma.rule.exception;

import com.sigma.rule.ErrorCodes;
import com.sigma.sigmacore.exception.SigmaException;

import java.text.MessageFormat;

/**
 * @author zhenpeng
 * 操作符不支持
 */
public class FactOpNotSupportException extends SigmaException {
    private static final String FORMAT = "不支持的運算符: {0}！";

    public FactOpNotSupportException(String op) {
        super(MessageFormat.format(FORMAT, op));
    }
}
