package com.sigma.sigmacore.aspects;

import com.sigma.sigmacore.utils.RetryConfig;

import java.lang.annotation.*;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/6-12:54
 * desc: SigmaRetry 註解
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface SigmaRetry {
    /**
     * 配置
     *
     * @return RetryConfig配置
     */
    Class<?> config() default RetryConfig.class;
}
