package com.sigma.redis.api;

import com.sigma.redis.entity.CacheBean;

import java.util.List;

public interface CacheAPI {
    /**
     * 传入key获取缓存json，需要用fastjson转换为对象
     *
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 保存缓存
     *
     * @param key
     * @param value
     * @param expireMin
     */
    void set(String key, Object value, int expireMin);

    /**
     * 保存缓存
     *
     * @param key
     * @param value
     * @param expireMin
     * @param desc
     */
    void set(String key, Object value, int expireMin, String desc);

    /**
     * 移除单个缓存
     *
     * @param key
     * @return
     */
    Long remove(String key);

    /**
     * 移除多个缓存
     *
     * @param keys
     * @return
     */
    Long remove(String... keys);

    /**
     * 按前缀移除缓存
     *
     * @param pre
     * @return
     */
    Long removeByPre(String pre);

    /**
     * 通过前缀获取缓存信息
     *
     * @param pre
     * @return
     */
    List<CacheBean> getCacheBeanByPre(String pre);

    /**
     * 获取所有缓存对象信息
     *
     * @return
     */
    List<CacheBean> listAll();

    /**
     * 是否启用缓存
     *
     * @return
     */
    boolean isEnabled();

    /**
     * 加入系统标志缓存
     *
     * @param key
     * @return
     */
    String addSys(String key);
}
