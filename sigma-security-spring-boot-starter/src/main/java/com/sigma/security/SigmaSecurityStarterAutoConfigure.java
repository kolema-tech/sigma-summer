package com.sigma.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huston.peng
 * @version 1.0.0
 * date-time: 2019/5/16-22:39
 * desc:
 **/
@Configuration
@EnableConfigurationProperties(SigmaSecurityProperties.class)
public class SigmaSecurityStarterAutoConfigure {

    @Autowired
    private SigmaSecurityProperties sigmaSecurityProperties;

    @Bean
    @ConditionalOnMissingBean
    CorsFilter corsFilter() {
        return new CorsFilter();
    }
}
