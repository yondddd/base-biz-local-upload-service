package com.ruijing.base.local.upload.web.console.console.req;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-08-28
 */
public class ObjectsDelReq implements Serializable {
    
    private static final long serialVersionUID = -3644384378998989212L;
    
    private String bucket;
    private List<String> keys;
    
    public String getBucket() {
        return bucket;
    }
    
    public ObjectsDelReq setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }
    
    public List<String> getKeys() {
        return keys;
    }
    
    public ObjectsDelReq setKeys(List<String> keys) {
        this.keys = keys;
        return this;
    }
    
    @Override
    public String toString() {
        return "ObjectsDelReq{" +
                "bucket='" + bucket + '\'' +
                ", keys=" + keys +
                '}';
    }
    
}
