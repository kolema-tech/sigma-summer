package com.sigma.rule.drools;

/**
 * Drools 规则编译异常
 */
public class DroolsBuildException extends RuntimeException {

    public DroolsBuildException(String message) {
        super(message);
    }

    public DroolsBuildException(String message, Throwable cause) {
        super(message, cause);
    }
}
