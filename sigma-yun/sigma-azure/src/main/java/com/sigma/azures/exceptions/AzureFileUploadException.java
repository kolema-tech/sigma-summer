package com.sigma.azures.exceptions;

import com.sigma.sigmacore.constant.SigmaResponseCodes;
import com.sigma.sigmacore.exception.SigmaException;

/**
 * @author huston.peng
 * @version 1.0.0
 * date-time: 2019/6/21-14:39
 * desc:
 **/
public class AzureFileUploadException extends SigmaException {
    public AzureFileUploadException() {
        super();
    }

    public AzureFileUploadException(String message) {
        super(message);
    }

    public AzureFileUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public AzureFileUploadException(Throwable cause) {
        super(cause);
    }

    protected AzureFileUploadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public SigmaException withArguments(Object... arguments) {
        return super.withArguments(arguments);
    }

    @Override
    public String getErrorCode() {
        return SigmaResponseCodes.Cloud.Azure.AZURE_UPLOAD_FILE_EXCEPTION;
    }

    @Override
    public Object[] getArguments() {
        return super.getArguments();
    }
}
