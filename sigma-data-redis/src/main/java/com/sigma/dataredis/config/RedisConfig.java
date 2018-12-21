package com.sigma.dataredis.config;

import com.sigma.dataredis.serializer.KryoRedisSerializer;
import com.sigma.dataredis.serializer.Lz4FastRedisSerializer;
import lombok.experimental.var;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author SteveGlory Zeng.
 * @version 1.0
 * date-time: 2018/7/26-17:03
 * desc:
 **/
@Configuration
public class RedisConfig {

    /**
     * 使用Kryo 进行数据压缩
     *
     * @param factory
     * @return
     */
    @Bean(name = "kryoRedisCompress")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

        var stringRedisSerializer = new StringRedisSerializer();
        var kryoRedisSerializer = new KryoRedisSerializer<Object>();

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(kryoRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(kryoRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }


    /**
     * 使用LZ4进行数据压缩
     *
     * @param factory
     * @return
     */
    @Bean(name = "lz4RedisTemplate")
    public RedisTemplate<String, Object> lz4RedisTemplate(RedisConnectionFactory factory) {

        var stringRedisSerializer = new StringRedisSerializer();
        var lz4FastRedisSerializer = new Lz4FastRedisSerializer<Object>();

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(lz4FastRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(lz4FastRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
