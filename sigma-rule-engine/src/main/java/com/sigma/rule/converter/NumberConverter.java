package com.sigma.rule.converter;

import com.sigma.rule.exception.FactDataTypeNotMatchException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pzdn
 * 数值轉換器
 */
@Slf4j
public class NumberConverter implements FactConverter<Double> {

    @Override
    public Double convert(Object value) throws FactDataTypeNotMatchException {
        try {
            return Double.parseDouble(value.toString());
        } catch (Exception ex) {
            log.error("验证失败", ex);
            throw new FactDataTypeNotMatchException("", ex.getMessage());
        }
    }
}
