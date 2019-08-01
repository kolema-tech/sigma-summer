package com.sigma.samples;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huston.peng
 * @version 1.0.5
 * date-time: 2019/6/14-15:38
 * desc:
 **/
@SpringBootApplication(scanBasePackages = "com.sigma")
@Api(value = "App")
@RestController
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @ApiOperation(value = "get method")
    @GetMapping("/api/user")
    public String getUser() {
        return "pzdn2009";
    }
}
