package com.ruijing.base.local.upload.web.s3.service.impl;

import com.ruijing.base.local.upload.config.SystemConfig;
import com.ruijing.base.local.upload.model.Bucket;
import com.ruijing.base.local.upload.util.DateUtil;
import com.ruijing.base.local.upload.web.s3.options.PutBucketOptions;
import com.ruijing.base.local.upload.web.s3.service.BucketService;
import com.ruijing.base.local.upload.web.s3.utils.BucketUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;

/**
 * @Description: buck service
 * @Author: WangJieLong
 * @Date: 2024-05-28
 */
@Service
public class BucketServiceImpl implements BucketService {

    private final SystemConfig systemConfig;

    public BucketServiceImpl(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }


    @Override
    public void putBucket(String bucketName, PutBucketOptions opts) {
        if (!BucketUtil.isBaseMetaBucketName(bucketName)) {
//            if (S3Util.checkValidBucketNameStrict(bucketName)){
//
//            }
        }
        String dirPath = systemConfig.getDataPath() + bucketName;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Bucket bucket = new Bucket();
        bucket.setName(bucketName);
        bucket.setCreationDate(DateUtil.getDateFormatToSecond(new Date()));
    }

}
