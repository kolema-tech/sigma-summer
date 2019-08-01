package com.sigma.rule.exception;

import com.sigma.sigmacore.exception.SigmaException;

import java.text.MessageFormat;

/**
 * @author zhenpeng
 * 事實值為null
 */
public class FactValueNullException extends SigmaException {

    private static final String FORMAT = "事實:{0} 值為null!";


    public FactValueNullException(String factName) {
        super(MessageFormat.format(FORMAT, factName));
    }
}
