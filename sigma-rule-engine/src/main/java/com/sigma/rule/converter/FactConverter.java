package com.sigma.rule.converter;


import com.sigma.rule.exception.FactDataTypeNotMatchException;

/**
 * @author zhenpeng
 * 類型轉換器
 */
public interface FactConverter<T> {
    /**
     * 驗證并返回
     *
     * @param value
     * @return
     * @throws FactDataTypeNotMatchException
     */
    T convert(Object value) throws FactDataTypeNotMatchException;
}
