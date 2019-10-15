package com.sigma.aliyunstarter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
@EnableConfigurationProperties(AliOssProperties.class)
@ConditionalOnBean(AliOssProperties.class)
public class AliOssStarterAutoConfigure {

    @Bean
    @ConditionalOnMissingBean
    public AliOssProperties aliOssProperties() {
        return new AliOssProperties();
    }

    @Bean
    AliOssService aliOssService() {
        return new AliOssService(aliOssProperties());
    }

}
