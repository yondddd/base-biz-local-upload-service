package com.ruijing.base.local.upload.web.s3.server.service.impl;

import com.ruijing.base.local.upload.web.s3.server.req.PutBucketReq;
import com.ruijing.base.local.upload.web.s3.server.service.BucketService;
import org.springframework.stereotype.Service;

/**
 * @author yond
 * @date 7/1/2024
 * @description
 */
@Service
public class BucketServiceImpl implements BucketService {

    @Override
    public void putBucket(PutBucketReq req) {
        // bucket 名字检查
    }

}
