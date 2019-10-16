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

    private String appId;

    private String merchantId;

    private String appKey;
}
