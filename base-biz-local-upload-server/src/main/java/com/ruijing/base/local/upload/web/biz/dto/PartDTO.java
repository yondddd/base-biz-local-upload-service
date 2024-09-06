package com.ruijing.base.local.upload.web.biz.dto;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-09-06
 */
public class PartDTO {
    
    private String eTag;
    private int partNumber;
    
    public String geteTag() {
        return eTag;
    }
    
    public PartDTO seteTag(String eTag) {
        this.eTag = eTag;
        return this;
    }
    
    public int getPartNumber() {
        return partNumber;
    }
    
    public PartDTO setPartNumber(int partNumber) {
        this.partNumber = partNumber;
        return this;
    }
    
    @Override
    public String toString() {
        return "PartDTO{" +
                "eTag='" + eTag + '\'' +
                ", partNumber=" + partNumber +
                '}';
    }
}
