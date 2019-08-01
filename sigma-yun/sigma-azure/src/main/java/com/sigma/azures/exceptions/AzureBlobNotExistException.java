package com.sigma.azures.exceptions;

import com.sigma.sigmacore.constant.SigmaResponseCodes;
import com.sigma.sigmacore.exception.SigmaException;

/**
 * @author huston.peng
 * @version 1.0.6
 * date-time: 2019/6/21-14:42
 * desc:
 **/
public class AzureBlobNotExistException extends SigmaException {
    public AzureBlobNotExistException() {
        super();
    }

    public AzureBlobNotExistException(String message) {
        super(message);
    }

    public AzureBlobNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public AzureBlobNotExistException(Throwable cause) {
        super(cause);
    }

    protected AzureBlobNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public SigmaException withArguments(Object... arguments) {
        return super.withArguments(arguments);
    }

    @Override
    public String getErrorCode() {
        return SigmaResponseCodes.Cloud.Azure.AZURE_FILE_NOT_EXIST_EXCEPTION;
    }

    @Override
    public Object[] getArguments() {
        return super.getArguments();
    }
}
