package com.ruijing.base.local.upload.web.s3.server.req;

import com.ruijing.base.local.upload.web.s3.metadata.Metadata;

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
    private Metadata metadata;
    
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
    
    public Metadata getMetadata() {
        return metadata;
    }
    
    public ObjectPutReq setMetadata(Metadata metadata) {
        this.metadata = metadata;
        return this;
    }
    
    @Override
    public String toString() {
        return "ObjectPutReq{" +
                "bucket='" + bucket + '\'' +
                ", key='" + key + '\'' +
                ", inputStream=" + inputStream +
                ", metadata=" + metadata +
                '}';
    }
    
}
