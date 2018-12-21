package com.sigma.logging;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/10/21-19:40
 * desc:
 **/
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "sigma.log")
public class PerformanceLogConfig {
    private String url;
    private String systemCode;
    private String source;
}
