package com.ruijing.base.local.upload.web.s3.server.service.impl;

import com.ruijing.base.local.upload.config.SystemConfig;
import com.ruijing.base.local.upload.web.s3.server.req.*;
import com.ruijing.base.local.upload.web.s3.server.resp.CompleteMultipartUploadResult;
import com.ruijing.base.local.upload.web.s3.server.resp.InitiateMultipartUploadResult;
import com.ruijing.base.local.upload.web.s3.server.resp.MultiUploadPartResult;
import com.ruijing.base.local.upload.web.s3.server.service.ObjectService;
import com.ruijing.fundamental.common.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectServiceImpl.class);
    
    @Override
    public String putObject(ObjectPutReq req) {
        String bucket = "/" + systemConfig.getDataPath() + req.getBucket();
        // bucket exist
        Path bucketPath = Paths.get(bucket);
        if (!Files.exists(bucketPath)) {
            return "bucket do not exist";
        }
        String object = bucket + "/" + req.getKey();
        Path objectPath = Paths.get(object);
        try {
            Path parentDir = objectPath.getParent();
            if (parentDir != null) {
                Files.createDirectories(parentDir);
            }
            Files.copy(req.getInputStream(), objectPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return object;
    }
    
    @Override
    public Path getObject(ObjectGetReq req) {
        String key = "/" + systemConfig.getDataPath() + req.getBucket() + req.getKey();
        return Paths.get(key);
    }
    
    @Override
    public void delObject(ObjectDelReq req) {
        String key = "/" + systemConfig.getDataPath() + req.getBucket() + req.getKey();
        Path path = Paths.get(key);
        if (!Files.exists(path)) {
            LOGGER.error("<|>ObjectServiceImpl_delObject<|>req:{}<|>", JsonUtils.toJson(req));
            return;
        }
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public InitiateMultipartUploadResult createMultipartUpload(MultipartUploadInitReq req) {
        return null;
    }
    
    @Override
    public MultiUploadPartResult uploadPart(MultiUploadPartReq req) {
        return null;
    }
    
    @Override
    public CompleteMultipartUploadResult completeMultipartUpload(MultipartUploadCompleteReq req) {
        return null;
    }
    
    @Override
    public void abortMultipartUpload(MultipartUploadAbortReq req) {
    
    }
    
}
