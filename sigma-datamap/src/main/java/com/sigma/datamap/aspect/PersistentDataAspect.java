package com.sigma.datamap.aspect;

import com.sigma.sigmacore.aspects.SigmaRetry;
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
public class PersistentDataAspect {

    public PersistentDataAspect() {
        log.debug("PersistentDataAspect已被加載");
    }

    @Around(value = "@annotation(persistentData)")
    public Object around(ProceedingJoinPoint pjp, PersistentData persistentData) throws Throwable {
        return pjp.proceed();
    }
}
