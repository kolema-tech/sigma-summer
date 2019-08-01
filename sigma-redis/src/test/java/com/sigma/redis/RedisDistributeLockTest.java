//package com.sigma.redis;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class RedisDistributeLockTest {
//
//    @Autowired
//    RedisDistributeLock redisDistributeLock;
//
//    @Test
//    public void lock() throws Exception {
//        Thread t1 = new Thread(() -> {
//            try {
//                System.out.println(Thread.currentThread().getName());
//                redisDistributeLock.lock();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        Thread t2 = new Thread(() -> {
//            try {
//                System.out.println(Thread.currentThread().getName());
//                redisDistributeLock.lock();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        t1.start();
//        t2.start();
//
//        Thread.sleep(10000);
//    }
//}
