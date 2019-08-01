package com.sigma.cloudspba;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author huston.peng
 * @version 1.0.6
 * date-time: 2019/6/16-21:54
 * desc:
 **/
@EnableAdminServer
@EnableEurekaClient
@SpringBootApplication

public class SpringBootAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdminApplication.class, args);
    }
}
