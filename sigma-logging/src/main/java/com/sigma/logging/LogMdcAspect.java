package com.sigma.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.sigma.logging.SigmaLoggerMdcConstants.TRACE_ID;

/**
 * @author huston.peng
 * @version 1.0.6
 * date-time: 2019/6/16-23:24
 * desc:
 **/
@Aspect
@Component
public class LogMdcAspect {

    @Pointcut("@annotation(org.springframework.scheduling.annotation.Async)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MDC.put(TRACE_ID, UUID.randomUUID().toString().replace("-", ""));
        Object result = point.proceed();
        MDC.remove(TRACE_ID);
        return result;
    }
}