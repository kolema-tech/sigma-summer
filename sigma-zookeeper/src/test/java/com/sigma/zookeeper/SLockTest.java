package com.sigma.zookeeper;

import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/8-10:48
 * desc: SLock 測試
 **/
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SLockTest {

    @Autowired
    ZkClient zkClient;

    @Test
    public void test() throws InterruptedException {
        Thread t1 = new Thread("t1") {
            public void run() {
                sharedLock();
            }
        };
        Thread t2 = new Thread("t2") {
            public void run() {
                sharedLock();
            }
        };
        t1.start();
        t2.start();

        Thread.sleep(2000);
    }

    private void sharedLock() {

        CuratorFramework client = zkClient.getClient();

        var interProcessMutex = new InterProcessMutex(client, "/sigma/slock");

        try {
            if (interProcessMutex.acquire(500, TimeUnit.MILLISECONDS)) {
                System.out.println("hello world!");
                log.info("get lock");
                Thread.sleep(1001);
            } else {
                log.info("get lock2");

                throw new SigmaLockNotAllowedException("獲取不到鎖！", null);
            }
        } catch (Exception ex) {
            log.info("SLockAspect", ex);
        } finally {
            try {
                if (interProcessMutex.isAcquiredInThisProcess()) {
                    interProcessMutex.release();
                }
            } catch (Exception ex) {
                log.info("interProcessMutex.release", ex);
            }
        }
    }
}