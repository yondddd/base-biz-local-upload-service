package com.ruijing.base.local.upload.config;

import com.ruijing.pearl.annotation.PearlValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @Desc
 * @Author wsen
 * @Date
 **/
@Configuration
public class UploadResolver {

    @PearlValue(key = "file.max.upload.size", defaultValue = "104857600")
    private static long MAX_FILE_UPLOAD_SIZE;
    @PearlValue(key = "file.max.memory.size", defaultValue = "1048576")
    private static Integer MAX_FILE_MEMORY_SIZE;

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getCommonsMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(MAX_FILE_UPLOAD_SIZE);
        multipartResolver.setMaxInMemorySize(MAX_FILE_MEMORY_SIZE);
        return multipartResolver;
    }

}
