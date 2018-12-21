package com.sigma.security.oauth2;

import lombok.experimental.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/27-16:09
 * desc: 配置
 **/
@Configuration
@EnableConfigurationProperties(Oauth2ClientProperties.class)
public class Oauth2ClientConfig {

    @Autowired
    private Oauth2ClientProperties oauth2ClientProperties;

    /**
     * 構造 客戶端證書模式
     *
     * @return
     */
    @Bean("sigmaClientCredentialsResourceDetails")
    public ClientCredentialsResourceDetails resourceDetails() {
        var details = new ClientCredentialsResourceDetails();
        details.setId(oauth2ClientProperties.getId());
        details.setAccessTokenUri(oauth2ClientProperties.getAccessTokenUrl());
        details.setClientId(oauth2ClientProperties.getClientId());
        details.setClientSecret(oauth2ClientProperties.getClientSecret());
        details.setAuthenticationScheme(AuthenticationScheme.header);
        return details;
    }

    /**
     * OAuth Rest模板
     *
     * @return
     */
    @Bean("sigmaOauth2RestTemplate")
    public OAuth2RestTemplate oAuth2RestTemplate() {
        final OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(resourceDetails(), new DefaultOAuth2ClientContext());
        oAuth2RestTemplate.setRequestFactory(new Netty4ClientHttpRequestFactory());
        return oAuth2RestTemplate;
    }
}
