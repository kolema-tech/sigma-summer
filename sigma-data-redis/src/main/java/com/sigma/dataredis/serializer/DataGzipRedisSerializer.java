package com.sigma.dataredis.serializer;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author SteveGlory Zeng.
 * @version 1.0
 * date-time: 2018/7/26-17:10
 * desc:
 **/
@Slf4j
public class DataGzipRedisSerializer<T> implements RedisSerializer<T> {

    public static final int BUFFER_SIZE = 4096;
    private RedisSerializer redisSerializer;

    public DataGzipRedisSerializer(RedisSerializer redisSerializer) {
        this.redisSerializer = redisSerializer;
    }

    /**
     * Gzip 数据压缩
     *
     * @param redisData
     * @return
     * @throws SerializationException
     */
    @Override
    public byte[] serialize(@Nullable Object redisData) throws SerializationException {
        if (redisData == null) {
            return new byte[0];
        }
        try {
            byte[] tempByte = redisSerializer.serialize(redisData);
            @Cleanup ByteArrayOutputStream bos = new ByteArrayOutputStream();
            @Cleanup GZIPOutputStream gzip = new GZIPOutputStream(bos);

            gzip.write(tempByte);
            gzip.close();
            bos.close();
            return bos.toByteArray();
        } catch (IOException e) {
            throw new SerializationException("Data Serialization failed", e);
        }
    }

    /**
     * Gzip 数据解压
     *
     * @param bytes
     * @return
     * @throws SerializationException
     */
    @Override
    public T deserialize(@Nullable byte[] bytes) throws SerializationException {

        if (bytes == null || bytes.length == 0) {
            return null;
        }

        try {
            @Cleanup ByteArrayOutputStream bos = new ByteArrayOutputStream();
            @Cleanup ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            @Cleanup GZIPInputStream gzip = new GZIPInputStream(bis);

            byte[] tempByte = new byte[BUFFER_SIZE];
            int n;
            while ((n = gzip.read(tempByte, 0, BUFFER_SIZE)) > 0) {
                bos.write(tempByte, 0, n);
            }
            T obj = (T) redisSerializer.deserialize(bos.toByteArray());

            gzip.close();
            bis.close();
            return obj;
        } catch (IOException e) {
            throw new SerializationException("Data Serialization failed", e);
        }
    }

}
