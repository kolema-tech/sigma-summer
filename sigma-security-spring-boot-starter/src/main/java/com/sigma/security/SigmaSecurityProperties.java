package com.sigma.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author huston.peng
 * @version 1.0.8
 * date-time: 2019-10-
 * desc:
 **/
@Configuration
@ConfigurationProperties(prefix = "sigma.security")
@Data
public class SigmaSecurityProperties {

    /**
     * 是否启用
     */
    private Boolean enableOpsCors = false;
}
