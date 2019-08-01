package com.sigma.dataredis.id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author zen peng.
 * @version 1.0.3
 * date-time: 2018/12/21-15:55
 * desc:
 **/
@Service
public abstract class BaseRedisIdGenerator implements RedisIdGenerator {

    private static volatile RedisAtomicLong redisAtomicLong = null;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * redis key
     *
     * @return
     */
    @Override
    public abstract String key();

    /**
     * 過期時間
     *
     * @return
     */
    @Override
    public abstract long timeout();

    @Override
    public Long generate() {

        if (redisAtomicLong == null) {
            redisAtomicLong = new RedisAtomicLong(key(), Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        }

        long incrementAndGet = redisAtomicLong.incrementAndGet();
        if (incrementAndGet == 1 && timeout() > 0) {
            redisAtomicLong.expire(timeout(), TimeUnit.SECONDS);
        }

        return incrementAndGet;
    }
}
