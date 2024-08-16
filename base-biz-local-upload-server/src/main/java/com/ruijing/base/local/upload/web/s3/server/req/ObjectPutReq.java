package com.ruijing.base.local.upload.web.s3.server.req;

import java.io.InputStream;

/**
 * @Description: put object
 * @Author: WangJieLong
 * @Date: 2024-07-25
 */
public class ObjectPutReq {
    
    private String bucketName;
    private String objectName;
    private InputStream inputStream;
    
    public static ObjectPutReq custom() {
        return new ObjectPutReq();
    }
    
    public String getBucketName() {
        return bucketName;
    }
    
    public ObjectPutReq setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }
    
    public String getObjectName() {
        return objectName;
    }
    
    public ObjectPutReq setObjectName(String objectName) {
        this.objectName = objectName;
        return this;
    }
    
    public InputStream getInputStream() {
        return inputStream;
    }
    
    public ObjectPutReq setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
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
