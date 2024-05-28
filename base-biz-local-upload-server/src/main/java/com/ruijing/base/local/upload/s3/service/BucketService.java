package com.ruijing.base.local.upload.s3.service;

import com.ruijing.base.local.upload.s3.options.PutBucketOptions;

/**
 * @Description: bucket interface
 * @Author: WangJieLong
 * @Date: 2024-05-28
 */
public interface BucketService {

    void putBucket(String bucketName, PutBucketOptions opts);
    
}
