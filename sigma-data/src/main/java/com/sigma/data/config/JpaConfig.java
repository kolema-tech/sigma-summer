package com.sigma.data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/26-17:19
 * desc: JPA 配置，默認開啟Auditing配置
 **/
@Configuration
@EnableJpaAuditing
public class JpaConfig {
}
