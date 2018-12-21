package com.sigma.feign;

import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/11-15:13
 * desc:
 **/
@Configuration
public class FeignConfig {

    /**
     * 配置请求重试
     */
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(200, 2000, 3);
    }

    /**
     * 打印请求日志
     *
     * @return
     */
    @Bean
    public feign.Logger.Level multipartLoggerLevel() {
        return feign.Logger.Level.FULL;
    }

    /**
     * 设置请求超时时间
     * 默认
     * public Options() {
     * this(10 * 1000, 60 * 1000);
     * }
     */
    @Bean
    Request.Options feignOptions() {
        return new Request.Options(60 * 1000, 60 * 1000);
    }

//    @Bean //使用原生的
//    public Contract feignContract(){
//        return new feign.Contract.Default();
//    }

}