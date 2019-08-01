package com.sigma.sigmacore.aspects;

import java.lang.annotation.*;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/6-14:56
 * desc: 執行時間度量
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface SigmaMetrics {
    /**
     * api名稱
     * 如果為空，則取方法名
     *
     * @return 名稱
     */
    String apiName() default "";
}
