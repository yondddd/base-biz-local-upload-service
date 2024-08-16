package com.ruijing.base.local.upload.web.s3.server.req;

import com.ruijing.fundamental.api.annotation.Model;

import java.io.Serializable;

/**
 * @Description: delete bucket
 * @Author: WangJieLong
 * @Date: 2024-08-16
 */
@Model("delete bucket")
public class BucketDelReq implements Serializable {
    
    private static final long serialVersionUID = 3620520541567446376L;
    
    public static BucketDelReq custom() {
        return new BucketDelReq();
    }
    
    private String bucketName;
    
    public String getBucketName() {
        return bucketName;
    }
    
    public BucketDelReq setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }
    
    @Override
    public String toString() {
        return "DelBucketReq{" +
                "bucketName='" + bucketName + '\'' +
                '}';
    }
}
