package com.sigma.sdk.interfaces;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiNetwork {
    /**
     * 读取超时时间
     *
     * @return
     */
    private int readTimeout;

    /**
     * 连接超时时间
     *
     * @return
     */
    private int connectTimeout;

    /**
     * 重試次數
     */
    private int retryTime;

    /**
     * 總的超時時間
     */
    private int totalTimeout;
}
