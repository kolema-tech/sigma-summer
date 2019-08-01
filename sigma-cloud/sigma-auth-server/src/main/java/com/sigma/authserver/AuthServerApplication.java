package com.sigma.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author huston.peng
 * @version 1.0.6
 * date-time: 2019/7/8-13:49
 * desc:
 **/
@SpringBootApplication(scanBasePackages = {"com.sigma.*"})
@EnableDiscoveryClient
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }
}
