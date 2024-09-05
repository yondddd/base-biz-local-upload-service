package com.ruijing.base.local.upload.web.s3.server.service.impl;

import com.ruijing.base.local.upload.constant.SysConstant;
import com.ruijing.base.local.upload.util.UUIDUtil;
import com.ruijing.base.local.upload.web.s3.metadata.Metadata;
import com.ruijing.base.local.upload.web.s3.server.req.*;
import com.ruijing.base.local.upload.web.s3.server.resp.CompleteMultipartUploadResult;
import com.ruijing.base.local.upload.web.s3.server.resp.InitiateMultipartUploadResult;
import com.ruijing.base.local.upload.web.s3.server.resp.MultiUploadPartResult;
import com.ruijing.base.local.upload.web.s3.server.service.ObjectService;
import com.ruijing.fundamental.common.util.JsonUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectServiceImpl.class);
    
    @Override
    public String putObject(ObjectPutReq req) {
        Metadata metadata = req.getMetadata();
        String bucket = "/" + SysConstant.dataPath + req.getBucket();
        String key = bucket + "/" + req.getKey();
        String file = key + "/part.1";
        String json = key + "/xl.json";
        Path filePath = Paths.get(file);
        try {
            Path parentDir = filePath.getParent();
            if (parentDir != null) {
                Files.createDirectories(parentDir);
            }
            Files.copy(req.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            metadata.getStat().setLastModified(String.valueOf(System.currentTimeMillis()));
            Path metaPath = Paths.get(json);
            // write metadata
            Files.write(metaPath, JsonUtils.toJson(metadata).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return key;
    }
    
    @Override
    public Pair<Path, Path> getObject(ObjectGetReq req) {
        String key = "/" + SysConstant.dataPath + req.getBucket() + req.getKey();
        String file = key + "/part.1";
        String metadata = key + "/xl.json";
        return Pair.of(Paths.get(file), Paths.get(metadata));
    }
    
    @Override
    public void delObject(ObjectDelReq req) {
        String key = "/" + SysConstant.dataPath + req.getBucket() + req.getKey();
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
        String uploadId = UUIDUtil.generateId();
        String tempPath = SysConstant.tempPath + "/" + uploadId + "/";
        Path path = Paths.get(tempPath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return InitiateMultipartUploadResult.custom()
                .setBucket(req.getBucket())
                .setKey(req.getKey())
                .setUploadId(uploadId);
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
