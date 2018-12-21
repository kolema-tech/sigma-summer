package com.sigma.data;

import com.sigma.sigmacore.config.SwaggerApiInfo;
import com.sigma.sigmacore.web.SigmaResponse;
import io.swagger.annotations.ApiOperation;
import lombok.experimental.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@SpringBootApplication(scanBasePackages = "com.sigma.*")
@RestController
@RequestMapping("/api/")
public class SigmaDataApplication {

    @Autowired
    PersonRepository personRepository;

    public static void main(String[] args) {
        SpringApplication.run(SigmaDataApplication.class, args);
    }

    @Bean
    public SwaggerApiInfo swaggerApiInfo() {
        return SwaggerApiInfo.builder()
                .title("sigma-kolema")
                .description("new starting...")
                .termsOfServiceUrl("http://www.kolema-ai.com/")
                .version("1.5.2")
                .build();
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ApiOperation(value = "你好")
    public SigmaResponse helloWorld() throws InterruptedException {

        var list = personRepository.findAll();
        return SigmaResponse.create(list);
    }
}
