package com.sigma.zookeeper.annotation;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/8-10:13
 * desc: 共享鎖標記
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface SLock {

    /**
     * 鍵
     *
     * @return
     */
    String key() default "/sigma/zkslock";

    /**
     * 默認等待時間
     *
     * @return
     */
    int waitTime() default 1000;
}
