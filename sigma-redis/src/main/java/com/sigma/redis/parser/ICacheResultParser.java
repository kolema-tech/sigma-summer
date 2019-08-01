package com.sigma.redis.parser;

import java.lang.reflect.Type;

/**
 * cache结果解析
 */
public interface ICacheResultParser {
    /**
     * 解析结果
     *
     * @param value
     * @param returnType
     * @param origins
     * @return
     */
    Object parse(String value, Type returnType, Class<?>... origins);
}
