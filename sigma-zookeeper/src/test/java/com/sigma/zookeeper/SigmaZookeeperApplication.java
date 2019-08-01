package com.sigma.zookeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.sigma.*"})
public class SigmaZookeeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(SigmaZookeeperApplication.class, args);
    }
}
