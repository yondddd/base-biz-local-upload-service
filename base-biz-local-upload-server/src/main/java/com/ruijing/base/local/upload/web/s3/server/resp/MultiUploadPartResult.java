package com.ruijing.base.local.upload.web.s3.server.resp;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-09-03
 */
public class MultiUploadPartResult {
    
    private String eTag;
    private String partNum;
    
    public String geteTag() {
        return eTag;
    }
    
    public MultiUploadPartResult seteTag(String eTag) {
        this.eTag = eTag;
        return this;
    }
    
    public String getPartNum() {
        return partNum;
    }
    
    public MultiUploadPartResult setPartNum(String partNum) {
        this.partNum = partNum;
        return this;
    }
    
    @Override
    public String toString() {
        return "MultiUploadPartResult{" +
                "eTag='" + eTag + '\'' +
                ", partNum='" + partNum + '\'' +
                '}';
    }
}
