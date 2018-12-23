package com.sigma.mockserver.aspects;

import com.sigma.mockserver.service.MockService;
import com.sigma.web.ThreadContextHolder;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
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
@Order(value = 9999)
public class SigmaMockServerAspect {

    @Autowired
    MockService mockService;

    public SigmaMockServerAspect() {
        log.debug("SigmaMockServerAspect已被加載");
    }

    @Around(value = "@annotation(sigmaMock)")
    public Object around(ProceedingJoinPoint pjp, SigmaMock sigmaMock) throws Throwable {
        var mockData = ThreadContextHolder.mockData();
        if (mockData) {
            return mockService.getValue(sigmaMock);
        }

        return pjp.proceed();
    }
}
