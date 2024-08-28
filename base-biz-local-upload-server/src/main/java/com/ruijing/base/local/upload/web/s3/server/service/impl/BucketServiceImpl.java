package com.ruijing.base.local.upload.web.s3.server.service.impl;

import com.ruijing.base.local.upload.config.SystemConfig;
import com.ruijing.base.local.upload.web.s3.server.req.BucketDelReq;
import com.ruijing.base.local.upload.web.s3.server.req.BucketPutReq;
import com.ruijing.base.local.upload.web.s3.server.resp.ListAllMyBucketsResult;
import com.ruijing.base.local.upload.web.s3.server.service.BucketService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public void putBucket(BucketPutReq req) {
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
    
    @Override
    public void deleteBucket(BucketDelReq req) {
        String bucketName = req.getBucketName();
        Path path = Paths.get("/" + systemConfig.getDataPath() + bucketName);
        boolean exists = Files.exists(path);
        if (!exists) {
            return;
        }
        if (!Files.isDirectory(path)) {
            return;
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            boolean isEmpty = !stream.iterator().hasNext();
            if (isEmpty) {
                Files.delete(path);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public ListAllMyBucketsResult listBuckets() {
        Path path = Paths.get("/" + systemConfig.getDataPath());
        List<ListAllMyBucketsResult.Bucket> buckets = new ArrayList<>();
        for (File file : Objects.requireNonNull(path.toFile().listFiles())) {
            Path filePath = file.toPath();
            BasicFileAttributes attributes = null;
            try {
                attributes = Files.readAttributes(filePath, BasicFileAttributes.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ListAllMyBucketsResult.Bucket bucket = new ListAllMyBucketsResult.Bucket();
            bucket.setName(file.getName());
            bucket.setCreationDate(attributes.creationTime().toString());
            buckets.add(bucket);
        }
        ListAllMyBucketsResult result = new ListAllMyBucketsResult();
        result.setBuckets(buckets);
        result.setOwner(new ListAllMyBucketsResult.Owner());
        return result;
    }
    
}
