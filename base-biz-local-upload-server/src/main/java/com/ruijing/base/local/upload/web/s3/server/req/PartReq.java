package com.ruijing.base.local.upload.web.s3.server.req;

import javax.xml.bind.annotation.XmlElement;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-09-03
 */
public class PartReq {
    
    private String eTag;
    private int partNumber;
    
    @XmlElement(name = "ETag")
    public String getETag() {
        return eTag;
    }
    
    public void setETag(String eTag) {
        this.eTag = eTag;
    }
    
    @XmlElement(name = "PartNumber")
    public int getPartNumber() {
        return partNumber;
    }
    
    public void setPartNumber(int partNumber) {
        this.partNumber = partNumber;
    }
}
