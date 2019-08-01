package com.sigma.webswagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author huston.peng
 * @version 1.0.5
 * date-time: 2019/6/13-22:03
 * desc:
 **/
@Configuration
@ConfigurationProperties(prefix = "swagger")
@Data
public class SwaggerProperties {

    /**
     * 是否启用swagger
     */
    private boolean enable = true;

    private String title = "Sigma Api";

    private String description = "Sigma Api of Kolema";

    private String termsOfServiceUrl = "http://www.kolema-ai.com/";

    private String basePackage = "com.sigma";

    private String basePath = "/**";

    private String version = "v1";

    private String license = "Apache License Version 2.0";

    private String licenseUrl = "https://www.apache.org/licenses/LICENSE-2.0";

    private String contactName = "huston.peng";

    private String contactUrl = "https://www.github.com/kolema-tech";

    private String contactEmail = "1050244110@qq.com";

    private String clientId = "";

    private String clientSecret = "";

    private String accessTokenUri = "http://localhost:8080/oauth/token";

    /**
     * 多个API名称
     */
    private String[] apiNames = new String[]{""};
}
