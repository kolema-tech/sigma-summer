package com.kolema.devapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018-06-
 * desc:
 **/
@SpringBootApplication(scanBasePackages = {"com.sigma", "com.kolema"})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

}
