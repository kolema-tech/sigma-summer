package com.sigma.security.oauth2;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/27-16:13
 * desc: oauth 客戶端屬性
 **/
@Getter
@Setter
@ConfigurationProperties(prefix = "sigma.oauth2.client")
public class Oauth2ClientProperties {
    /**
     * 資源服務器Id
     */
    private String id;

    /**
     * token url
     */
    private String accessTokenUrl;

    /**
     * 客戶端id
     */
    private String clientId;

    /**
     * 客戶端密碼
     */
    private String clientSecret;
}

