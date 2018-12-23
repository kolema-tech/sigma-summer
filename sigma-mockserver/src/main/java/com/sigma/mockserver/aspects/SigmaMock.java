package com.sigma.mockserver.aspects;

import com.sigma.sigmacore.utils.RetryConfig;
import org.springframework.http.HttpMethod;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface SigmaMock {
    String id() default "mock-id";
    Class<?> type() default String.class;
}

