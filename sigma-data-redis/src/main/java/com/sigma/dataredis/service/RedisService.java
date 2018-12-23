package com.sigma.dataredis.service;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018-06-
 * desc:
 **/
public interface RedisService {

    /**
     * 读取值
     *
     * @param key
     * @return
     */
    Object getValue(String key);

}
