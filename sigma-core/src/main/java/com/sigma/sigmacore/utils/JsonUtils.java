package com.sigma.sigmacore.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author zhenpeng
 * @version 1.0
 * date-time: 2018/6/4-8:26
 * desc:  JSON 工具類
 **/
public class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    //父类的静态变量->父类的静态初始化块->子类的静态变量->子类的静态初始化块
    static {
        //默認忽略位置屬性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectMapper getMapper() {
        return objectMapper;
    }

    /**
     * 將對象序列化為JSON
     *
     * @param object 對象
     * @return json字符串
     * @throws IOException IO異常(包含JSON異常)
     */
    public static String serialize(Object object) throws IOException {
        return serialize(object, false);
    }

    /**
     * 將對象序列化為JSON
     *
     * @param object     對象
     * @param withPretty 是否格式化
     * @return json字符串
     * @throws IOException IO異常(包含JSON異常)
     */
    public static String serialize(Object object, boolean withPretty) throws IOException {
        Writer write = new StringWriter();
        if (withPretty) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(write, object);
        } else {
            objectMapper.writeValue(write, object);
        }

        return write.toString();
    }

    /**
     * 將對象序列化為JSON
     *
     * @param objectMapper 自配置對象
     * @param object       對象
     * @param withPretty   是否格式化
     * @return Json字符串
     * @throws IOException IO異常
     */
    public static String serialize(ObjectMapper objectMapper, Object object, boolean withPretty) throws IOException {
        Writer write = new StringWriter();
        if (withPretty) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(write, object);
        } else {
            objectMapper.writeValue(write, object);
        }

        return write.toString();
    }

    /**
     * 反序列化為T
     *
     * @param json      json字符串
     * @param classType 類別
     * @param <T>       泛型
     * @return 對象
     * @throws IOException IO異常（包括JSON異常）
     */
    public static <T> T deserialize(String json, Class<T> classType) throws IOException {
        checkNotNull(json);
        return (T) objectMapper.readValue(json, TypeFactory.rawClass(classType));
    }


    /**
     * 反序列化為T
     *
     * @param json    json字符串
     * @param typeRef 類別
     * @param <T>     泛型
     * @return 對象
     * @throws IOException IO異常（包括JSON異常）
     */
    public static <T> T deserialize(String json, TypeReference<T> typeRef) throws IOException {
        return objectMapper.readValue(json, typeRef);
    }

    /**
     * 反序列化為T
     *
     * @param objectMapper 自配置Mapper
     * @param json         json字符串
     * @param typeRef      類型
     * @param <T>          泛型
     * @return 對象
     * @throws IOException IO異常（包括JSON異常）
     */
    public static <T> T deserialize(ObjectMapper objectMapper, String json, TypeReference<T> typeRef) throws IOException {
        return objectMapper.readValue(json, typeRef);
    }
}
