package com.sigma.wxpay.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author huston.peng
 * @version 1.0.8
 * date-time: 2019-10-
 * desc: 配置
 **/
@Data
@Configuration
@ConfigurationProperties(value = "sigmapay.wxpay")
public class SigmaWxpayProperties {

    /**
     * AppId
     */
    private String appId;

    /**
     * 商户ID
     */
    private String merchantId;

    /**
     * Key
     */
    private String appKey;
}
