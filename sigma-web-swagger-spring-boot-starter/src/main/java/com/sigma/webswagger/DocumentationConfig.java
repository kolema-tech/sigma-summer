package com.sigma.webswagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author huston.peng
 * @version 1.0.6
 * date-time: 2019/6/16-21:25
 * desc:
 **/
@Component
@Primary
@ConditionalOnProperty(name = "swagger.enable", matchIfMissing = false)
public class DocumentationConfig implements SwaggerResourcesProvider {

    @Autowired
    SwaggerProperties swaggerProperties;

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public List<SwaggerResource> get() {
        List resources = new ArrayList<>();

        if (swaggerProperties.getApiNames() != null) {
            Arrays.stream(swaggerProperties.getApiNames()).forEach(s ->
                    {
                        if (!StringUtils.hasLength(s)) {
                            resources.add(swaggerResource(applicationName, "/v2/api-docs", "2.0"));
                        } else {
                            resources.add(swaggerResource(s, "/" + s + "/v2/api-docs", "2.0"));
                        }
                    }
            );
        }

        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}