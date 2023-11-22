package com.example.demo01.src.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean logApiFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new LogApiFilter());
        bean.addUrlPatterns("/*");
        bean.setName("logApiFilter");
        return bean;
    }
}

