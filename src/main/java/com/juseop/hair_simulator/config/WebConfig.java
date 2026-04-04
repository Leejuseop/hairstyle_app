package com.juseop.hair_simulator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/storage/**")
                .addResourceLocations("file:///C:/hairstyle_app/users/");

        registry.addResourceHandler("/styles/**")
                .addResourceLocations("file:///C:/hairstyle_app/data/hairstyle/");
    }
}