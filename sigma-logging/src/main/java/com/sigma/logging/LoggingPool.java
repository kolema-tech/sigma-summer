package com.sigma.logging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.var;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/9/27-12:13
 * desc:
 **/
@Deprecated
public class LoggingPool {

    private static volatile LoggingPool ourInstance = new LoggingPool();
    private BlockingQueue<LogObject> blockingQueue = new LinkedBlockingQueue<>();

    private LoggingPool() {
        ExecutorService e = Executors.newFixedThreadPool(2);
        e.submit(new Send(this));
        e.submit(new Send(this));
    }

    public static LoggingPool getInstance() {
        return ourInstance;
    }

    public void push(LogObject logObject) {
        blockingQueue.add(logObject);
    }

    public LogObject poll() {
        LogObject result = null;
        try {
            result = this.blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class LogObject {
        private String url;
        private Object logObject;
    }

    class Send implements Runnable {

        private RestTemplate restTemplate;
        private LoggingPool loggingPool;

        public Send(LoggingPool loggingPool) {
            this.loggingPool = loggingPool;
            restTemplate = new RestTemplate();
        }

        @Override
        public void run() {
            while (true) {

                var logObject = loggingPool.poll();
                if (logObject != null) {
                    var response = restTemplate.postForObject(logObject.getUrl(), logObject.getLogObject(), String.class);
                    System.out.println("send to es success:" + response);
                }

                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
