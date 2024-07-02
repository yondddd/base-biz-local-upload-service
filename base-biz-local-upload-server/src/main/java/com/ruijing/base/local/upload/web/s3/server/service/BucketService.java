package com.ruijing.base.local.upload.web.s3.server.service;

import com.ruijing.base.local.upload.web.s3.server.req.PutBucketReq;

/**
 * @author yond
 * @date 7/1/2024
 * @description bucket service
 */
public interface BucketService {

    void putBucket(PutBucketReq req);

}
