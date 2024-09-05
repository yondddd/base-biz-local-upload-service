package com.ruijing.base.local.upload.web.s3.server.resp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-08-28
 */
@XmlRootElement(name = "InitiateMultipartUploadResult")
public class InitiateMultipartUploadResult {
    
    private String bucket;
    private String key;
    private String uploadId;
    
    public static InitiateMultipartUploadResult custom() {
        return new InitiateMultipartUploadResult();
    }
    
    @XmlElement(name = "Bucket")
    public String getBucket() {
        return bucket;
    }
    
    public InitiateMultipartUploadResult setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }
    
    @XmlElement(name = "Key")
    public String getKey() {
        return key;
    }
    
    public InitiateMultipartUploadResult setKey(String key) {
        this.key = key;
        return this;
    }
    
    @XmlElement(name = "UploadId")
    public String getUploadId() {
        return uploadId;
    }
    
    public InitiateMultipartUploadResult setUploadId(String uploadId) {
        this.uploadId = uploadId;
        return this;
    }
    
    @Override
    public String toString() {
        return "InitiateMultipartUploadResult{" +
                "bucket='" + bucket + '\'' +
                ", key='" + key + '\'' +
                ", uploadId='" + uploadId + '\'' +
                '}';
    }
}
