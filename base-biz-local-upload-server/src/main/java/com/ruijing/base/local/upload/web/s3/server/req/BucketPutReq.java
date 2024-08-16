package com.ruijing.base.local.upload.web.s3.server.req;

import com.ruijing.base.local.upload.web.s3.server.options.PutBucketOptions;

/**
 * @author yond
 * @date 7/2/2024
 * @description put bucket request
 */
public class BucketPutReq {
    
    private String bucketName;
    
    private PutBucketOptions options;
    
    public static BucketPutReq custom() {
        return new BucketPutReq();
    }
    
    public String getBucketName() {
        return bucketName;
    }
    
    public BucketPutReq setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }
    
    public PutBucketOptions getOptions() {
        return options;
    }
    
    public BucketPutReq setOptions(PutBucketOptions options) {
        this.options = options;
        return this;
    }
    
    @Override
    public String toString() {
        return "PutBucketReq{" +
                "bucketName='" + bucketName + '\'' +
                ", options=" + options +
                '}';
    }
}
