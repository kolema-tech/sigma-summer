package com.sigma.dataredis.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import javax.xml.datatype.Duration;

/**
 * @author vick.zeng
 * date-time: 2018/10/10 10:05
 **/
public class KryoRedisSerializer<T> implements RedisSerializer<T> {

    /**
     * Kryo非线程安全，每个线程都需要new Kryo() {https://github.com/EsotericSoftware/kryo#thread-safety}
     */
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

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null) {
            return null;
        }
        Input input = new Input(bytes);
        T result = (T) kryoLocal.get().readClassAndObject(input);
        input.close();

        return result;
    }
}
