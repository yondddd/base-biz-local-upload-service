package com.ruijing.base.local.upload.s3.service.impl;

import com.ruijing.base.local.upload.s3.options.PutBucketOptions;
import com.ruijing.base.local.upload.s3.service.BucketService;
import com.ruijing.base.local.upload.util.s3.BucketUtil;
import com.ruijing.base.local.upload.util.s3.S3Util;
import org.springframework.stereotype.Service;

/**
 * @Description: buck service
 * @Author: WangJieLong
 * @Date: 2024-05-28
 */
@Service
public class BucketServiceImpl implements BucketService {
    
    @Override
    public void putBucket(String bucketName, PutBucketOptions opts) {
        if (!BucketUtil.isBaseMetaBucketName(bucketName)){
            if (S3Util.checkValidBucketNameStrict(bucketName)){
            
            }
        }
    }
    
}
