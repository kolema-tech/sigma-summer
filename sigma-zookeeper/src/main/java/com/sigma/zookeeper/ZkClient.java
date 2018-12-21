package com.sigma.zookeeper;

import com.sigma.zookeeper.config.SigmaZookeeperConfiguration;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/8-10:28
 * desc:
 **/
@Component
@Scope("prototype")
public class ZkClient {

    /**
     * zk 配置
     */
    @Autowired
    private SigmaZookeeperConfiguration configuration;

    /**
     * 緩存實例
     */
    private CuratorFramework client;

    public CuratorFramework getClient() {

        if (client != null) {
            return client;
        }


        ACLProvider aclProvider = new ACLProvider() {
            private List<ACL> acl;

            //初始化操作
            private synchronized void init() {
                if (acl == null) {
                    acl = ZooDefs.Ids.OPEN_ACL_UNSAFE;
                }
            }

            @Override
            public List<ACL> getDefaultAcl() {
                if (acl == null) {
                    init();
                }
                return this.acl;
            }

            @Override
            public List<ACL> getAclForPath(String path) {
                if (acl == null) {
                    init();
                }
                return this.acl;
            }
        };

        RetryPolicy retryPolicy = new RetryNTimes(3, 5000);
        client = CuratorFrameworkFactory.builder()
                .aclProvider(aclProvider)
                .connectionTimeoutMs(configuration.getConnectionTimeout())
                .connectString(configuration.getUrl())
                .maxCloseWaitMs(configuration.getMaxCloseWait())
                .retryPolicy(retryPolicy)
                .build();

        client.start();

        return this.client;
    }
}
