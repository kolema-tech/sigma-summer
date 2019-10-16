package com.sigma.wxpay.sdk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
public class DefaultWxPayConfig extends WXPayConfig {

    @Value("${wxpay.appid}")
    private String appId;

    @Value("${wxpay.merchantid}")
    private String merchantId;

    @Value("${wxpay.appkey}")
    private String appKey;

    @Override
    public String getAppID() {
        return appId;
    }

    @Override
    public String getMchID() {
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
