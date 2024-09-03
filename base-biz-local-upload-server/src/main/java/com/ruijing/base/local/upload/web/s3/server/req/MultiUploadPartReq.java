package com.ruijing.base.local.upload.web.s3.server.req;

import java.io.InputStream;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-09-03
 */
public class MultiUploadPartReq {
    
    private String bucket;
    private String key;
    private String uploadId;
    private Integer partNum;
    private InputStream inputStream;
    
    public static MultiUploadPartReq custom() {
        return new MultiUploadPartReq();
    }
    
    public String getBucket() {
        return bucket;
    }
    
    public MultiUploadPartReq setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }
    
    public String getKey() {
        return key;
    }
    
    public MultiUploadPartReq setKey(String key) {
        this.key = key;
        return this;
    }
    
    public String getUploadId() {
        return uploadId;
    }
    
    public MultiUploadPartReq setUploadId(String uploadId) {
        this.uploadId = uploadId;
        return this;
    }
    
    public Integer getPartNum() {
        return partNum;
    }
    
    public MultiUploadPartReq setPartNum(Integer partNum) {
        this.partNum = partNum;
        return this;
    }
    
    public InputStream getInputStream() {
        return inputStream;
    }
    
    public MultiUploadPartReq setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        return this;
    }
    
    @Override
    public String toString() {
        return "MultiUploadPartReq{" +
                "bucket='" + bucket + '\'' +
                ", key='" + key + '\'' +
                ", uploadId='" + uploadId + '\'' +
                ", partNum=" + partNum +
                ", inputStream=" + inputStream +
                '}';
    }
}
