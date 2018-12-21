package com.sigma.dataredis.id;

/**
 * @author zen peng.
 * @version 1.0.3
 * <p>
 * date-time: 2018/12/21-15:54
 * desc:
 **/
public interface RedisIdGenerator {

    /**
     * key值
     *
     * @return
     */
    String key();

    /**
     * 過期時間
     *
     * @return
     */
    long timeout();

    /**
     * 生成
     *
     * @return
     */
    Long generate();
}
