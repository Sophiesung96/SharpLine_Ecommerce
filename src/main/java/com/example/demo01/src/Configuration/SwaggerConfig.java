package com.example.springboot_ecommerce.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo()); // Integrate the apiInfo() method here
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SharpLine API")
                .description("Description of SharpLine API")
                .version("1.0.0")
                .termsOfServiceUrl("https://example.com/terms")
                .contact(new Contact("Sophie Sung", "https://example.com/contact", "sharpline@service.com"))
                .license("Apache 2.0 License")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .build();
    }
}

