package com.sigma.sigmacore.aspects;

import com.sigma.sigmacore.utils.RetryConfig;
import com.sigma.sigmacore.utils.RetryUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/6-12:53
 * desc: 攔截器
 **/
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Slf4j
public class SigmaRetryAspect {

    public SigmaRetryAspect() {
        log.debug("RetryerAspect已被加載");
    }

    @Around(value = "@annotation(sigmaRetry)")
    public Object around(ProceedingJoinPoint pjp, SigmaRetry sigmaRetry) throws IllegalAccessException, InstantiationException {

        Object result = RetryUtil.retry(() -> {
            try {
                return pjp.proceed();
            } catch (Throwable throwable) {
                return null;
            }
        }, (RetryConfig) sigmaRetry.config().newInstance());

        return result;
    }
}
