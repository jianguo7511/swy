package com.apiyoo.anthorization.swy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

//  addResourceHandler  url请求的路径

//  addResourceLocations图片存放的真实路径
        registry.addResourceHandler("/image/**").addResourceLocations("file:E://aiyoImg");
        super.addResourceHandlers(registry);
    }
}

