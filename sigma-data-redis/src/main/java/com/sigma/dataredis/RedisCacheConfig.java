package com.sigma.dataredis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sigma.sigmacore.utils.JsonUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huston.peng
 * @version 1.0.6
 * date-time: 2019/6/16-23:50
 * desc:
 **/
@Configuration
@EnableCaching
public class RedisCacheConfig {

    public static final String DEFAULT_KEY_GENERATOR = "keyGenerator";

    @Primary
    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("target", target.getClass().toGenericString());
            map.put("method", method.getName());
            if (params.length > 0) {
                int i = 0;
                for (Object o : params) {
                    map.put("params-" + i, o);
                    i++;
                }
            }

            byte[] hash = null;
            String s = null;
            try {
                String str = JsonUtils.serialize(map);
                hash = MessageDigest.getInstance("MD5").digest(str.getBytes(StandardCharsets.UTF_8));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            assert hash != null;
            s = MD5Encoder.encode(hash);

            return s;
        };
    }

    @Primary
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return new RedisCacheManager(
                RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                this.getRedisCacheConfigurationWithTtl(600)
        );
    }


    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
                RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(jackson2JsonRedisSerializer)
        ).entryTtl(Duration.ofSeconds(seconds));

        return redisCacheConfiguration;
    }
}
