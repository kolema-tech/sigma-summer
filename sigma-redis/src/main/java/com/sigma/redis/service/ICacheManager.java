package com.sigma.redis.service;

import com.sigma.redis.entity.CacheBean;
import com.sigma.redis.vo.CacheTree;

import java.util.List;

public interface ICacheManager {
    void removeAll();

    void remove(String key);

    void remove(List<CacheBean> caches);

    void removeByPre(String pre);

    List<CacheTree> getAll();

    List<CacheTree> getByPre(String pre);

    void update(String key, int hour);

    void update(List<CacheBean> caches, int hour);

    String get(String key);
}
