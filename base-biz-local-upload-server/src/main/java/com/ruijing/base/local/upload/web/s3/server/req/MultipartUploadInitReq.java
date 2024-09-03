package com.ruijing.base.local.upload.web.s3.server.req;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-09-03
 */
public class MultipartUploadInitReq {
    
    private String bucket;
    private String key;
    
    public static MultipartUploadInitReq custom() {
        return new MultipartUploadInitReq();
    }
    
    public String getBucket() {
        return bucket;
    }
    
    public MultipartUploadInitReq setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }
    
    public String getKey() {
        return key;
    }
    
    public MultipartUploadInitReq setKey(String key) {
        this.key = key;
        return this;
    }
    
    @Override
    public String toString() {
        return "MultipartUploadInitReq{" +
                "bucket='" + bucket + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
