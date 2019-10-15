package com.sigma.sigmacore.api;

import java.lang.annotation.*;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/8-19:06
 * desc: Api 配置
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ApiNetworkConfig {

    /**
     * 读取超时时间
     *
     * @return 读取超时时间
     */
    int readTimeout() default 2000;

    /**
     * 连接超时时间
     *
     * @return 连接超时时间
     */
    int connectTimeout() default 1000;


    int retryTime() default 3;


    int totalTimeout() default 10000;

}
