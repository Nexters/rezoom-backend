package com.nexters.rezoom.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by JaeeonJin on 2018-07-31.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * TODO : 현재 기본설정만 되어있는 상태. 수정 필요
     */

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
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


}

