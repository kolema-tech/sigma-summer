package com.sigma.web;

import com.sigma.sigmacore.api.BasicAuthPair;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/5-13:01
 * desc: basic auth 請求模板
 **/
@Component
public class BasicRestTemplate {
    private RestTemplateBuilder builder;

    public BasicRestTemplate(RestTemplateBuilder builder) {
        this.builder = builder;
    }


    public BasicRestTemplate withBasicAuthPair(BasicAuthPair basicAuthPair) {
        builder.basicAuthorization(basicAuthPair.getUserName(), basicAuthPair.getPassword());
        return this;
    }


    public BasicRestTemplate withReadTimeout(int millSeconds) {
        builder.setReadTimeout(millSeconds);
        return this;
    }


    public BasicRestTemplate withConnectTimeout(int millSeconds) {
        builder.setConnectTimeout(millSeconds);
        return this;
    }

    public RestTemplate build() {
        return builder.build();
    }
}
