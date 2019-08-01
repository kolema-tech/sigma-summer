package com.sigma.dataredis.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import lombok.extern.slf4j.Slf4j;
import net.jpountz.lz4.LZ4FrameInputStream;
import net.jpountz.lz4.LZ4FrameOutputStream;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import javax.xml.datatype.Duration;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author stevegloryzeng
 * @version 1.0
 * date-time: 2018/11/27 9:50
 * desc:
 **/
@Slf4j
public class Lz4FastRedisSerializer<T> implements RedisSerializer<T> {

    public static final int BUFFER_SIZE = 4096;
    private static final ThreadLocal<Kryo> kryoLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false);
        kryo.addDefaultSerializer(Duration.class, new JavaSerializer());
        return kryo;
    });

    @Override
    public byte[] serialize(T t) throws SerializationException {

        if (t == null) {
            return new byte[0];
        }

        Output output = new Output(2048, -1);
        kryoLocal.get().writeClassAndObject(output, t);
        byte[] result = output.toBytes();
        output.close();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        LZ4FrameOutputStream lzis = null;
        try {
            lzis = new LZ4FrameOutputStream(bos);
            lzis.write(result);
            lzis.close();
        } catch (IOException e) {
            log.error("LZ4 compress redis data failed", e);
        }

        return bos.toByteArray();
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {

        if (bytes == null || bytes.length == 0) {
            return null;
        }

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            InputStream bis = new ByteArrayInputStream(bytes);
            LZ4FrameInputStream lz4is = new LZ4FrameInputStream(bis);
            byte[] result = new byte[BUFFER_SIZE];
            int n;
            while ((n = lz4is.read(result, 0, BUFFER_SIZE)) > 0) {
                bos.write(result, 0, n);
            }

            Input input = new Input(bos.toByteArray());
            T t = (T) kryoLocal.get().readClassAndObject(input);

            input.close();
            lz4is.close();
            bis.close();
            bos.close();
            return t;
        } catch (IOException e) {
            log.error("LZ4 and KRYO deserialize redis data failed", e);
            return null;
        }

    }
}
