package com.ruijing.base.local.upload.web.s3.server.service.impl;

import com.ruijing.base.local.upload.config.SystemConfig;
import com.ruijing.base.local.upload.web.s3.server.req.PutBucketReq;
import com.ruijing.base.local.upload.web.s3.server.service.BucketService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author yond
 * @date 7/1/2024
 * @description
 */
@Service
public class BucketServiceImpl implements BucketService {
    
    @Resource
    private SystemConfig systemConfig;
    
    @Override
    public void putBucket(PutBucketReq req) {
        // bucket 名字检查
        String bucketName = req.getBucketName();
        Path path = Paths.get("/" + systemConfig.getDataPath() + bucketName);
        boolean exists = Files.exists(path);
        if (!exists) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
}
