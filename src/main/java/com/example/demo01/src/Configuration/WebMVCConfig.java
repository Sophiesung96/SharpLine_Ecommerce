package com.example.springboot_ecommerce.Configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory("category-image", registry);
        exposeDirectory("product-images", registry);
        exposeDirectory("user-pics", registry);
        exposeDirectory("brand_logo", registry);
        exposeDirectory("SITE_LOGO",registry);

    }

    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler(  "/"+dirName + "/**").addResourceLocations("file:" + uploadPath+"/");
    }

}
