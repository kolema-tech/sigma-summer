package com.sigma.zookeeper.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/8-11:58
 * desc: ZK 配置
 * TODO: using network define.
 **/
@Getter
@Setter
@Configuration
@ConfigurationProperties(value = "sigma.dist.zookeeper")
public class SigmaZookeeperConfiguration {

    /**
     * url
     */
    private String url;

    /**
     * 鏈接超時時間，毫秒為單位
     */
    private Integer connectionTimeout = 5 * 1000;

    /**
     * 最大超時時間，毫秒為單位
     */
    private Integer maxCloseWait = 10 * 1000;
}
