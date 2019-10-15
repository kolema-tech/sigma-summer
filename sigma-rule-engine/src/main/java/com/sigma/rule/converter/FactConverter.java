package com.sigma.rule.converter;


import com.sigma.rule.exception.FactDataTypeNotMatchException;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2019-09-22
 * desc:
 **/
public interface FactConverter<T> {

    /**
     * 转换
     * @param value 值
     * @return 对象
     * @throws FactDataTypeNotMatchException 类型不匹配
     */
    T convert(Object value) throws FactDataTypeNotMatchException;
}
