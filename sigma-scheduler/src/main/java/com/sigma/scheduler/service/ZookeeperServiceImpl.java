package com.sigma.scheduler.service;

import com.sigma.scheduler.schedule.SigmaScheduler;
import com.sigma.scheduler.zookeeper.ZkPath;
import com.sigma.zookeeper.ZkClient;
import lombok.experimental.var;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/26-19:14
 * desc: Zk 服務實現
 **/
@Service
public class ZookeeperServiceImpl implements ZookeeperService {

    private static HashMap<String, NodeCache> nodeCacheHashMap = new LinkedHashMap<>();

    @Autowired
    ZkClient zkClient;

    @Autowired
    SigmaScheduler sigmaScheduler;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void resume(ZkPath zkPath) {
        try {
            updateData(zkPath, "resume");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pause(ZkPath zkPath) {
        try {
            updateData(zkPath, "pause");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(ZkPath zkPath) {
        try {
            updateData(zkPath, "run");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancel(ZkPath zkPath) {
        try {
            updateData(zkPath, "cancel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cronUpdate(ZkPath zkPath) {
        try {
            updateData(zkPath, "cronUpdate");
        } catch (Exception e) {
            System.out.println("更新节点数据失败, e=" + e.getMessage());
        }
    }

    private void updateData(ZkPath zkPath, String context) throws Exception {

        var path = zkPath.toPath();

        nodeListen(path);

        var data = zkClient.getClient()
                .checkExists()
                .creatingParentContainersIfNeeded()
                .forPath(path);

        if (data == null) {
            zkClient.getClient()
                    .create()
                    .forPath(path, zkPath.getData().getBytes());
        } else {
            zkClient.getClient()
                    .setData()
                    .inBackground((client, event) -> {
                        System.out.println(client.getZookeeperClient());
                        System.out.println(event.getResultCode());
                        System.out.println(event.getPath());
                        System.out.println(event.getContext());
                    }, context)
                    .forPath(path, zkPath.getData().getBytes());
        }
    }

    public void nodeListen(String path) throws Exception {

        if (!nodeCacheHashMap.containsKey(path)) {
            var cache = new NodeCache(zkClient.getClient(), path);
            cache.start();
            cache.getListenable().addListener(() -> {
                var zkPath = ZkPath.createByString(cache.getCurrentData().getPath());
                var data = new String(cache.getCurrentData().getData());
                if (zkPath.getMaster()) {
                    if ("cron".equals(zkPath.getSubNode())) {
                        sigmaScheduler.cronUpdate(zkPath.getService(), zkPath.getService(), data);
                    } else if ("resume".equals(zkPath.getSubNode())) {
                        sigmaScheduler.resumeJob(zkPath.getService(), zkPath.getService());
                    } else if ("pause".equals(zkPath.getSubNode())) {
                        sigmaScheduler.pauseJob(zkPath.getService(), zkPath.getService());
                    } else if ("run".equals(zkPath.getSubNode())) {
                        sigmaScheduler.run(zkPath.getService(), zkPath.getService());
                    } else if ("cancel".equals(zkPath.getSubNode())) {

                    }
                    System.out.println("master:" + cache.getCurrentData().getPath() + "  變化的數據：" + data);
                } else {
                    //TODO: ws.
                    System.out.println("ws:" + cache.getCurrentData().getPath() + "  變化的數據：" + data);
                    simpMessagingTemplate.convertAndSend("/topic/greetings", "hello");
                }
            });

            nodeCacheHashMap.put(path, cache);
        }
    }
}
