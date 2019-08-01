package com.sigma.sigmacore.aspects;

import java.lang.annotation.*;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018-06-10
 * desc:
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Idempotent {
}
