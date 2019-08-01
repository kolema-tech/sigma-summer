package com.sigma.azures.exceptions;

import com.sigma.sigmacore.constant.SigmaResponseCodes;
import com.sigma.sigmacore.exception.SigmaException;

/**
 * @author huston.peng
 * @version 1.0.6
 * date-time: 2019/6/21-14:28
 * desc:
 **/
public class AzureConnectionException extends SigmaException {

    public AzureConnectionException() {
        super();
    }

    public AzureConnectionException(String message) {
        super(message);
    }

    public AzureConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public AzureConnectionException(Throwable cause) {
        super(cause);
    }

    protected AzureConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getErrorCode() {
        return SigmaResponseCodes.Cloud.Azure.AZURE_CONN_EXCEPTION;
    }
}
