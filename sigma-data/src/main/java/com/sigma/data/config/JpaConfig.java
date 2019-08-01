package com.sigma.data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author huston.peng
 * @version 1.0.0
 * date-time: 2019/5/19-15:52
 * desc:
 **/
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories
public class JpaConfig {
}
