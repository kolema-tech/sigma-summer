package com.sigma.rule.converter;

import com.sigma.rule.exception.FactDataTypeNotMatchException;
import com.sigma.sigmacore.SigmaConstant;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhenpeng
 * 布爾轉換器
 */
@Slf4j
public class BooleanConverter implements FactConverter<Boolean> {

    @Override
    public Boolean convert(Object value) throws FactDataTypeNotMatchException {
        try {
            String val = value.toString().toLowerCase();
            if (SigmaConstant.General.TRUE.equals(val)) {
                return true;
            } else if (SigmaConstant.General.FALSE.equals(val)) {
                return false;
            }
            throw new FactDataTypeNotMatchException("", val + "非bool");
        } catch (Exception ex) {
            log.error("验证失败", ex);
            throw new FactDataTypeNotMatchException("", ex.getMessage());
        }
    }
}
