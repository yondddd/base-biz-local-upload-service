package com.ruijing.base.local.upload.web.s3.server.req;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-09-03
 */
public class ObjectDelReq {
    
    private String bucket;
    private String key;
    
    public static ObjectDelReq custom() {
        return new ObjectDelReq();
    }
    
    public String getBucket() {
        return bucket;
    }
    
    public ObjectDelReq setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }
    
    public String getKey() {
        return key;
    }
    
    public ObjectDelReq setKey(String key) {
        this.key = key;
        return this;
    }
    
    @Override
    public String toString() {
        return "ObjectDelReq{" +
                "bucket='" + bucket + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
