package com.ruijing.base.local.upload.web.s3.service.impl;

import com.ruijing.base.local.upload.web.s3.options.PutBucketOptions;
import com.ruijing.base.local.upload.web.s3.service.BucketService;
import com.ruijing.base.local.upload.web.s3.utils.BucketUtil;
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
        if (!BucketUtil.isBaseMetaBucketName(bucketName)) {
//            if (S3Util.checkValidBucketNameStrict(bucketName)){
//
//            }
        }
    }

}
