package com.sigma.dataredis;


public enum RedisSerializerType {
    /**
     * json
     */
    JACKSON_2_JSON,

    /**
     * 压缩
     */
    KRYO,

    /**
     * 压缩
     */
    LZ_4;
}
