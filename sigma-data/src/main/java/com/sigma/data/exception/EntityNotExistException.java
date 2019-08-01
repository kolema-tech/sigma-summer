package com.sigma.data.exception;

import com.sigma.sigmacore.constant.SigmaResponseCodes;
import com.sigma.sigmacore.exception.SigmaException;

/**
 * @author huston.peng
 * @version 1.0.0
 * date-time: 2019/6/13-21:42
 * desc: 实体不存在返回的异常
 **/
public class EntityNotExistException extends SigmaException {

    @Override
    public String getErrorCode() {
        return SigmaResponseCodes.ENTITY_NOT_EXIST;
    }
}
