package com.goldrental.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map URL path /uploads/** to local folder c:/uploads/
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///c:/uploads/")
                .setCachePeriod(0); // disable caching during development
    }
}