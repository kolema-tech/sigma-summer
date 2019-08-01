package com.sigma.sigmacore.exception.buildin;

import com.sigma.sigmacore.constant.SigmaResponseCodes;
import com.sigma.sigmacore.exception.SigmaException;

public class SigmaAuthException extends SigmaException {

    @Override
    public String getErrorCode() {
        return SigmaResponseCodes.AUTH_ERROR_CODE;
    }
}
