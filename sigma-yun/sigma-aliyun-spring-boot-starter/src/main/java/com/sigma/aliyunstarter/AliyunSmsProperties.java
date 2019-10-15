package com.sigma.aliyunstarter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author huston.peng
 * @version 1.0.8
 * date-time: 2019-10-
 * desc:
 **/
@Configuration
@ConfigurationProperties(prefix = "aliyun.sms")
@Data
public class AliyunSmsProperties {

    /**
     * 子账号Key
     */
    private String accessKeyId;

    /**
     * 子账号secret
     */
    private String secret;

    /**
     * 签名
     */
    private String signName;
}
