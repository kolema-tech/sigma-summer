package com.sigma.wxpay.autoconfigure;

import com.sigma.wxpay.sdk.DefaultWxPayConfig;
import com.sigma.wxpay.sdk.wrapper.WxPayWrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author huston.peng
 * @version 1.0.8
 * date-time: 2019-10-
 * desc:
 **/
@EnableConfigurationProperties(SigmaWxpayProperties.class)
public class SigmaWxpayStarterAutoConfigure {

    @Autowired
    SigmaWxpayProperties sigmaWxpayProperties;

    @Bean
    public WxPayWrapperService wxPayWrapperService() {

        DefaultWxPayConfig wxPayConfig = new DefaultWxPayConfig(
                sigmaWxpayProperties.getAppId(),
                sigmaWxpayProperties.getMerchantId(),
                sigmaWxpayProperties.getAppKey());

        return new WxPayWrapperService(wxPayConfig);
    }

}
