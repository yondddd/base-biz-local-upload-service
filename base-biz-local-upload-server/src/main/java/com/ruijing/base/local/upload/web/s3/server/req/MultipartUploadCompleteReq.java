package com.ruijing.base.local.upload.web.s3.server.req;

import java.util.List;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-09-03
 */
public class MultipartUploadCompleteReq {
    
    private String uploadId;
    private String bucket;
    private String key;
    private List<PartReq> parts;
    
    public static MultipartUploadCompleteReq custom() {
        return new MultipartUploadCompleteReq();
    }
    
    public String getUploadId() {
        return uploadId;
    }
    
    public MultipartUploadCompleteReq setUploadId(String uploadId) {
        this.uploadId = uploadId;
        return this;
    }
    
    public String getBucket() {
        return bucket;
    }
    
    public MultipartUploadCompleteReq setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }
    
    public String getKey() {
        return key;
    }
    
    public MultipartUploadCompleteReq setKey(String key) {
        this.key = key;
        return this;
    }
    
    public List<PartReq> getParts() {
        return parts;
    }
    
    public MultipartUploadCompleteReq setParts(List<PartReq> parts) {
        this.parts = parts;
        return this;
    }
    
    @Override
    public String toString() {
        return "MultipartUploadCompleteReq{" +
                "uploadId='" + uploadId + '\'' +
                ", bucket='" + bucket + '\'' +
                ", key='" + key + '\'' +
                ", parts=" + parts +
                '}';
    }
}
