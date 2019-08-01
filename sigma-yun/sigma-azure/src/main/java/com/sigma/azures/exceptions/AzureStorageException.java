package com.sigma.azures.exceptions;

import com.sigma.sigmacore.constant.SigmaResponseCodes;
import com.sigma.sigmacore.exception.SigmaException;

/**
 * @author huston.peng
 * @version 1.0.6
 * date-time: 2019/6/21-14:34
 * desc:
 **/
public class AzureStorageException extends SigmaException {
    public AzureStorageException() {
        super();
    }

    public AzureStorageException(String message) {
        super(message);
    }

    public AzureStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public AzureStorageException(Throwable cause) {
        super(cause);
    }

    protected AzureStorageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getErrorCode() {
        return SigmaResponseCodes.Cloud.Azure.AZURE_STORAGE_EXCEPTION;
    }

    @Override
    public Object[] getArguments() {
        return super.getArguments();
    }
}
