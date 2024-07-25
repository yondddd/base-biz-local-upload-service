package com.ruijing.base.local.upload.web.s3.server.req;

/**
 * @Description: put object
 * @Author: WangJieLong
 * @Date: 2024-07-25
 */
public class PutObjectReq {
    
    private String bucketName;
    private String objectName;
    
    public static PutObjectReq custom() {
        return new PutObjectReq();
    }
    
    public String getBucketName() {
        return bucketName;
    }
    
    public PutObjectReq setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }
    
    public String getObjectName() {
        return objectName;
    }
    
    public PutObjectReq setObjectName(String objectName) {
        this.objectName = objectName;
        return this;
    }
    
    @Override
    public String toString() {
        return "PutObjectReq{" +
                "bucketName='" + bucketName + '\'' +
                ", objectName='" + objectName + '\'' +
                '}';
    }
}
