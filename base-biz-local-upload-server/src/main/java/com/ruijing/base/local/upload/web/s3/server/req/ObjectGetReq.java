package com.ruijing.base.local.upload.web.s3.server.req;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-09-03
 */
public class ObjectGetReq {
    
    private String bucket;
    private String key;
    
    public static ObjectGetReq custom() {
        return new ObjectGetReq();
    }
    
    public String getBucket() {
        return bucket;
    }
    
    public ObjectGetReq setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }
    
    public String getKey() {
        return key;
    }
    
    public ObjectGetReq setKey(String key) {
        this.key = key;
        return this;
    }
    
    @Override
    public String toString() {
        return "ObjectGetReq{" +
                "bucket='" + bucket + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
