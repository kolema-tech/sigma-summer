package com.sigma.rule.exception;

import com.sigma.sigmacore.exception.SigmaException;

import java.text.MessageFormat;

/**
 * @author zhenpeng
 * Fact數據類型不匹配
 */
public class FactDataTypeNotMatchException extends SigmaException {

    private static final String FORMAT = "事實:{0} 類型非法：{1}!";

    public FactDataTypeNotMatchException(String factName, String detail) {
        super(MessageFormat.format(FORMAT, factName, detail));
    }
}
