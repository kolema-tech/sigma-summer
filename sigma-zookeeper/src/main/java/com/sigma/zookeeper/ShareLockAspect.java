package com.sigma.zookeeper;

import com.sigma.zookeeper.annotation.SLock;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/8-10:15
 * desc: SLock
 **/
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Slf4j
public class ShareLockAspect {

    @Autowired
    ZkClient zkClient;

    public ShareLockAspect() {
        log.info("SLockAspect已被加載");
    }

    @Around(value = "@annotation(sLock)")
    public Object around(ProceedingJoinPoint pjp, SLock sLock) throws Throwable {

        CuratorFramework client = zkClient.getClient();

        var interProcessMutex = new InterProcessMutex(client, sLock.key());

        try {
            if (interProcessMutex.acquire(sLock.waitTime(), TimeUnit.MILLISECONDS)) {
                log.info("获得锁：" + sLock.key());
                return pjp.proceed();
            } else {
                throw new SigmaLockNotAllowedException("沒有獲取到鎖！", null);
            }
        } catch (Exception ex) {
            log.error("SLockAspect Exception", ex);
            throw ex;
        } finally {
            try {
                if (interProcessMutex.isAcquiredInThisProcess()) {
                    interProcessMutex.release();
                    log.debug("釋放鎖！");
                }
            } catch (Exception ex) {
                log.error("interProcessMutex.release", ex);
            }
        }
    }
}
