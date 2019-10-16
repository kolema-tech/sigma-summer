package com.sigma.wxpay.sdk;

import java.io.InputStream;

public class DefaultWxPayConfig extends WXPayConfig {

    private String appId;

    private String merchantId;

    private String appKey;

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
    public IWXPayDomain getWXPayDomain() {
        return new DefaultWXPayDomain();
    }
}
