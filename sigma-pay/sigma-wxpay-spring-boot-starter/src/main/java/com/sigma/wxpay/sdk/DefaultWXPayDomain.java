package com.sigma.wxpay.sdk;

import lombok.extern.slf4j.Slf4j;

import static com.sigma.wxpay.sdk.WXPayConstants.DOMAIN_API;

@Slf4j
public class DefaultWXPayDomain implements IWXPayDomain {
    @Override
    public void report(String domain, long elapsedTimeMillis, Exception ex) {
    }

    @Override
    public DomainInfo getDomain(WXPayConfig config) {
        return new DomainInfo(DOMAIN_API, true);
    }
}
