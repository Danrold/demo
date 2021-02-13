package com.example.demo.swagger;

import org.springframework.context.annotation.*;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo")).build().apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Demo",
                "Test app for LiderIT",
                "0.0.1",
                ApiInfo.DEFAULT.getTermsOfServiceUrl(),
                new Contact("Ivan Medvedev", "https://github.com/Danrold", "danrold@mail.ru"),
                ApiInfo.DEFAULT.getLicense(),
                ApiInfo.DEFAULT.getLicenseUrl(),
                ApiInfo.DEFAULT.getVendorExtensions()
        );
    }
}
