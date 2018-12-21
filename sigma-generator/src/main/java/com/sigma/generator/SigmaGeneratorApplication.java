package com.sigma.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhenpeng
 */
@SpringBootApplication
@MapperScan("com.sigma.generator.mapper")
public class SigmaGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SigmaGeneratorApplication.class, args);
    }
}
