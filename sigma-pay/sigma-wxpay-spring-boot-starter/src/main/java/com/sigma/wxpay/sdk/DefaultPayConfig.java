package com.sigma.wxpay.sdk;

import java.io.InputStream;

/**
 * @author huston.peng
 * @version 1.0。8
 * date-time: 2019-10-
 * desc: 配置默认实现
 **/
public class DefaultPayConfig extends WXPayConfig {

    private String appId;

    private String merchantId;

    private String appKey;

    public DefaultPayConfig(String appId, String merchantId, String appKey) {
        this.appId = appId;
        this.merchantId = merchantId;
        this.appKey = appKey;
    }

    @Override
    public String getAppId() {
        return appId;
    }

    @Override
    public String getMerchantId() {
        return merchantId;
    }

    @Override
    public String getKey() {
        return appKey;
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public PayDomain getWXPayDomain() {
        return new DefaultPayDomain();
    }
}
