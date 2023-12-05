package com.example.demo01.src;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;


@SpringBootApplication
@EnableOpenApi
public class Demo01Application {


    public static void main(String[] args) {
        SpringApplication.run(Demo01Application.class, args);

    }


}

