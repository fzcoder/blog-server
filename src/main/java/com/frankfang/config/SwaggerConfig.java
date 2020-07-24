package com.frankfang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
	@Bean
    public Docket docker() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.frankfang.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("blog项目")
                        .description("博客")
                        .contact(new Contact("frankfang", "https://github.com/0Quasimodo0", "xxxxxxxxxxx@xx.com"))
                        .version("v1.0")
                        .license("The Apache License")
                        .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                        .build());
    }
}
