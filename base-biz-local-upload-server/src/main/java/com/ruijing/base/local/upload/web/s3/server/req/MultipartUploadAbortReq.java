package com.ruijing.base.local.upload.web.s3.server.req;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-09-04
 */
public class MultipartUploadAbortReq {
    
    private String uploadId;
    private String bucket;
    private String key;
    
    public static MultipartUploadAbortReq custom() {
        return new MultipartUploadAbortReq();
    }
    
    public String getUploadId() {
        return uploadId;
    }
    
    public MultipartUploadAbortReq setUploadId(String uploadId) {
        this.uploadId = uploadId;
        return this;
    }
    
    public String getBucket() {
        return bucket;
    }
    
    public MultipartUploadAbortReq setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }
    
    public String getKey() {
        return key;
    }
    
    public MultipartUploadAbortReq setKey(String key) {
        this.key = key;
        return this;
    }
    
    @Override
    public String toString() {
        return "MultipartUploadAbortReq{" +
                "uploadId='" + uploadId + '\'' +
                ", bucket='" + bucket + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
