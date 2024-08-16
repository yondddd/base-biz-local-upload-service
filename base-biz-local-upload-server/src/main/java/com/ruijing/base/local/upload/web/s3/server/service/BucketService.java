package com.ruijing.base.local.upload.web.s3.server.service;

import com.ruijing.base.local.upload.web.s3.server.req.BucketDelReq;
import com.ruijing.base.local.upload.web.s3.server.req.BucketPutReq;

/**
 * @author yond
 * @date 7/1/2024
 * @description bucket service
 */
public interface BucketService {
    
    void putBucket(BucketPutReq req);
    
    void deleteBucket(BucketDelReq req);
}
