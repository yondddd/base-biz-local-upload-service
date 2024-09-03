package com.ruijing.base.local.upload.web.s3.server.resp;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-09-03
 */
public class CompleteMultipartUploadResult {
    
    private String eTag;
    
    public String geteTag() {
        return eTag;
    }
    
    public CompleteMultipartUploadResult seteTag(String eTag) {
        this.eTag = eTag;
        return this;
    }
    
    @Override
    public String toString() {
        return "CompleteMultipartUploadResult{" +
                "eTag='" + eTag + '\'' +
                '}';
    }
    
}
