package com.nexters.rezoom.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JaeeonJin on 2018-07-31.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(Principal.class)
                .globalOperationParameters(apiParameter())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nexters.rezoom.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Re`Zoom Application REST API",
                "",
                "0.1",
                "취준진담",
                "",
                "",
                "");
        return apiInfo;
    }

    private List<Parameter> apiParameter() {
        List<Parameter> global = new ArrayList();
        global.add(new ParameterBuilder().name("Authorization").
                description("Access Token").parameterType("header").
                required(false).modelRef(new ModelRef("string")).build());

        return global;
    }
}

