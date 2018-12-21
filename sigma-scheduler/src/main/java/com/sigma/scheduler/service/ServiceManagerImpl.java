package com.sigma.scheduler.service;

import com.google.common.collect.Lists;
import com.sigma.scheduler.zookeeper.ZkPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pzdn
 * 单例的服务管理
 */
@Service
public class ServiceManagerImpl implements ServiceManager {


    @Autowired
    ZookeeperService zookeeperService;

    @Value("${sigma.scheduler.master:false}")
    Boolean master;

    @Override
    public List<String> getAllJobs() {
        return Lists.newArrayList();
    }

    @Override
    public void resume(String serviceName) {
        zookeeperService.resume(ZkPath.createResume(serviceName, master));
    }

    @Override
    public void pause(String serviceName) {
        zookeeperService.pause(ZkPath.createPause(serviceName, master));
    }

    @Override
    public void run(String serviceName) {
        zookeeperService.run(ZkPath.createRun(serviceName, master));
    }

    @Override
    public void cancel(String serviceName) {
        zookeeperService.cancel(ZkPath.createCancel(serviceName, master));
    }

    @Override
    public void cronUpdate(String serviceName, String cron) {
        zookeeperService.cronUpdate(ZkPath.createCron(serviceName, master, cron));
    }
}
