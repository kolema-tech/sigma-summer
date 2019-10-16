package com.sigma.wxpay.sdk;

import lombok.extern.slf4j.Slf4j;

import static com.sigma.wxpay.sdk.PayConstants.DOMAIN_API;

/**
 * @author huston.peng
 * @version 1.0.8
 * date-time: 2019-10-
 * desc:
 **/
@Slf4j
public class DefaultPayDomain implements PayDomain {
    @Override
    public void report(String domain, long elapsedTimeMillis, Exception ex) {
    }

    @Override
    public DomainInfo getDomain(BasePayConfig config) {
        return new DomainInfo(DOMAIN_API, true);
    }
}
