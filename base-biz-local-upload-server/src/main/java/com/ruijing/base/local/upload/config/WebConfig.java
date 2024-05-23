package com.ruijing.base.local.upload.config;

import com.ruijing.base.local.upload.intecept.S3Intecept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private S3Intecept s3Intecept;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(s3Intecept).addPathPatterns("/s3/**");
    }
}
