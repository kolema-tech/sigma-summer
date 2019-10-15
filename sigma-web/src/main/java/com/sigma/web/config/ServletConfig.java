package com.sigma.web.config;

import com.sigma.web.filter.RequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.sigma.web.SigmaWebConstants.DEFAULT_PATH_PATTERN_2;

/**
 * servlet 配置类
 *
 * @author ware zhang
 * @version 1.0.0
 * date 2018/10/30 17:40
 */
@Configuration
public class ServletConfig {


    @Bean
    public FilterRegistrationBean registrationBean() {
        FilterRegistrationBean<RequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestFilter());
        registrationBean.addUrlPatterns(DEFAULT_PATH_PATTERN_2);
        return registrationBean;
    }

}
