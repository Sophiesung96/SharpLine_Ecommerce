package com.example.demo01.src;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;


@SpringBootApplication
@EnableOpenApi
public class SpringbootEcommerceApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringbootEcommerceApplication.class, args);

    }


}

