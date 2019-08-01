package com.sigma.scheduler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/7-16:25
 * desc:
 **/
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface CronJobConfig {

    /**
     * Job 名稱
     *
     * @return
     */
    String jobName() default "";

    /**
     * 組名稱
     *
     * @return
     */
    String groupName() default "";

    /**
     * 表達式
     *
     * @return
     */
    String expression() default "0 0/1 * * * ?";
}
