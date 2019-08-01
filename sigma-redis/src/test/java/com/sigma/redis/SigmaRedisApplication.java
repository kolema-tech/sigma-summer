package com.sigma.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.sigma.*"})
public class SigmaRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SigmaRedisApplication.class, args);
    }
}
