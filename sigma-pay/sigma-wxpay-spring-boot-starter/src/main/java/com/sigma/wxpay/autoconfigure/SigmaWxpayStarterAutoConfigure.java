package com.sigma.wxpay.autoconfigure;

import com.sigma.wxpay.sdk.DefaultPayConfig;
import com.sigma.wxpay.sdk.wrapper.PayWrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huston.peng
 * @version 1.0.8
 * date-time: 2019-10-
 * desc:
 **/
@Configuration
@EnableConfigurationProperties(SigmaWxpayProperties.class)
public class SigmaWxpayStarterAutoConfigure {

    @Autowired
    SigmaWxpayProperties sigmaWxpayProperties;

    @Bean
    public PayWrapperService wxPayWrapperService() {

        DefaultPayConfig wxPayConfig = new DefaultPayConfig(
                sigmaWxpayProperties.getAppId(),
                sigmaWxpayProperties.getMerchantId(),
                sigmaWxpayProperties.getAppKey());

        return new PayWrapperService(wxPayConfig);
    }

}
