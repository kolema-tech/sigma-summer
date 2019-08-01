package com.sigma.sigmacore.exception;

import com.sigma.sigmacore.constant.SigmaResponseCodes;
import lombok.Getter;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/5-14:09
 * desc: Sigma 框架 異常類
 **/
public class SigmaException extends RuntimeException {

    /**
     * 错误代码
     */
    @Getter
    private String errorCode = SigmaResponseCodes.ERROR_CODE;

    /**
     * 参数
     */
    @Getter
    private Object[] arguments;

    public SigmaException() {
        super();
    }

    public SigmaException(String message) {
        super(message);
    }

    public SigmaException(String message, Throwable cause) {
        super(message, cause);
    }

    public SigmaException(Throwable cause) {
        super(cause);
    }

    protected SigmaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * 带上参数
     *
     * @param arguments 参数列表
     * @return this
     */
    public SigmaException withArguments(Object... arguments) {
        this.arguments = arguments;
        return this;
    }
}
