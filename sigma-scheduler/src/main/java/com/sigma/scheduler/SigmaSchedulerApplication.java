package com.sigma.scheduler;

import com.sigma.sigmacore.config.SwaggerApiInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/26-20:52
 * desc: 應用
 **/
@SpringBootApplication(scanBasePackages = {"com.sigma.*"})
public class SigmaSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SigmaSchedulerApplication.class, args);
    }

    @Bean
    public SwaggerApiInfo swaggerApiInfo() {
        return SwaggerApiInfo.builder()
                .title("sigma-调度器")
                .description("蓝天博士")
                .version("1.0.1")
                .build();
    }

}
