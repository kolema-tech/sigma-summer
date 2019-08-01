package com.sigma.tran.tcc.service;

import com.sigma.tran.tcc.entity.NormalTcc;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018-06-
 * desc: Tcc接口
 **/
public interface TccService<T extends NormalTcc> {

    /**
     * 尝试调用
     *
     * @param entity
     */
    void trying(T entity);

    /**
     * 确认
     *
     * @param entity
     */
    void confirm(T entity);

    /**
     * 取消
     *
     * @param entity
     */
    void cancel(T entity);

    /**
     * 自动取消
     */
    void autoCancel();
}
