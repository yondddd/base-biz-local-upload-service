package com.ruijing.base.local.upload.web.s3.server.service.impl;

import com.ruijing.base.local.upload.config.SystemConfig;
import com.ruijing.base.local.upload.web.s3.server.req.ObjectPutReq;
import com.ruijing.base.local.upload.web.s3.server.service.ObjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-07-25
 */
@Service
public class ObjectServiceImpl implements ObjectService {
    
    @Resource
    private SystemConfig systemConfig;
    
    @Override
    public String putObject(ObjectPutReq req) {
        String bucket = "/" + systemConfig.getDataPath() + req.getBucketName();
        // bucket exist
        Path bucketPath = Paths.get(bucket);
        if (!Files.exists(bucketPath)) {
            return "bucket do not exist";
        }
        String object = bucket + "/" + req.getObjectName();
        Path objectPath = Paths.get(object);
        try {
            Files.copy(req.getInputStream(), objectPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return object;
    }
    
}
