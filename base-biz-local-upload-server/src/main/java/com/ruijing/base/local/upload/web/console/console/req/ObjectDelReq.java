package com.ruijing.base.local.upload.web.console.console.req;

import java.io.Serializable;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-08-28
 */
public class ObjectDelReq implements Serializable {
    
    private static final long serialVersionUID = -3644384378998989212L;
    
    private String bucket;
    private String key;
    
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
