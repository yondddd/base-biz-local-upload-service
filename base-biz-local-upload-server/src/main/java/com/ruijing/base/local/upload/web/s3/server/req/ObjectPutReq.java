package com.ruijing.base.local.upload.web.s3.server.req;

import java.io.InputStream;

/**
 * @Description: put object
 * @Author: WangJieLong
 * @Date: 2024-07-25
 */
public class ObjectPutReq {
    
    private String bucket;
    private String key;
    private InputStream inputStream;
    
    public static ObjectPutReq custom() {
        return new ObjectPutReq();
    }
    
    public String getBucket() {
        return bucket;
    }
    
    public ObjectPutReq setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }
    
    public String getKey() {
        return key;
    }
    
    public ObjectPutReq setKey(String key) {
        this.key = key;
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
                "bucketName='" + bucket + '\'' +
                ", objectName='" + key + '\'' +
                '}';
    }
}
