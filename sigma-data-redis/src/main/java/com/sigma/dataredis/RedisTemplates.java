package com.sigma.dataredis;

import com.sigma.dataredis.serializer.KryoRedisSerializer;
import com.sigma.dataredis.serializer.Lz4FastRedisSerializer;
import lombok.experimental.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * @author SteveGlory Zeng.
 * @version 1.0
 * date-time: 2018/7/26-17:03
 * desc:
 **/
@Component
@Configuration
public class RedisTemplates {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 使用Kryo 进行数据压缩
     *
     * @param factory
     * @return
     */
    @Bean(name = "kryoRedisCompress")
    public RedisTemplate<String, Object> kryoRedisCompress(RedisConnectionFactory factory) {

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

    /**
     * 泛型的生成器，解决命包名的问题
     *
     * @param clazz
     * @param <String>
     * @param <V>
     * @return
     */
    public <String, V> RedisTemplate<String, V> getGenericRedisTemplate(Class<V> clazz, RedisSerializerType redisSerializerType) {
        var stringRedisSerializer = new StringRedisSerializer();
        var redisSerializer = getRedisSerializer(clazz, redisSerializerType);

        RedisTemplate<String, V> redisTemplate = new RedisTemplate<String, V>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);

        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    private <V> RedisSerializer<V> getRedisSerializer(Class<V> clazz, RedisSerializerType redisSerializerType) {

        RedisSerializer<V> redisSerializer = null;
        switch (redisSerializerType) {
            case JACKSON_2_JSON:
                redisSerializer = new Jackson2JsonRedisSerializer<V>(clazz);
                break;
            case KRYO:
                redisSerializer = new KryoRedisSerializer<V>();
                break;
            case LZ_4:
                redisSerializer = new Lz4FastRedisSerializer<V>();
                break;
        }

        return redisSerializer;
    }

//    @PostConstruct
//    public void PushRedisServicePostConstruct() {
//        userRedisTemplate = redisConfig.getJacksonStringTemplate(UpdateUserInfoRequest.class);
//    }
}
