package com.sigma.rule.exception;

import com.sigma.rule.ErrorCodes;
import com.sigma.sigmacore.exception.SigmaException;

import java.text.MessageFormat;

/**
 * @author zhenpeng
 * 事實鍵不存在
 */
public class FactKeyNotFoundException extends SigmaException {

    private static final String FORMAT = "事實:{0}鍵不存在!";

    public FactKeyNotFoundException(String name) {
        super(MessageFormat.format(FORMAT, name));
    }
}
