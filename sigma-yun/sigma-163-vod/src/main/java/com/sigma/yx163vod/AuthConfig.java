package com.sigma.yx163vod;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class AuthConfig {

    @Value("${wyvod.appkey}")
    private String appKey;

    @Value("${wyvod.appsecret}")
    private String appSecret;

}
