package com.sigma.scheduler.service;

import java.util.List;

/**
 * @author pzdn
 * 服务管理器
 */
public interface ServiceManager {

    List<String> getAllJobs();

    void resume(String serviceName);

    void pause(String serviceName);

    void run(String serviceName);

    void cancel(String serviceName);

    void cronUpdate(String serviceName, String cron);
}
