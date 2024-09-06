package com.ruijing.base.local.upload.web.s3.client.dto;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-09-06
 */
public class CreateMultipartUploadResult {
    
    private String uploadId;
    private String key;
    
    public String getUploadId() {
        return uploadId;
    }
    
    public CreateMultipartUploadResult setUploadId(String uploadId) {
        this.uploadId = uploadId;
        return this;
    }
    
    public String getKey() {
        return key;
    }
    
    public CreateMultipartUploadResult setKey(String key) {
        this.key = key;
        return this;
    }
    
    @Override
    public String toString() {
        return "CreateMultipartUploadResult{" +
                "uploadId='" + uploadId + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
