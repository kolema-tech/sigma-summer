package com.sigma.wxpay.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author huston.peng
 * @version 1.0.8
 * date-time: 2019-10-
 * desc: 配置
 **/
@Data
@ConfigurationProperties(value = "sigma.pay.wxpay")
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
