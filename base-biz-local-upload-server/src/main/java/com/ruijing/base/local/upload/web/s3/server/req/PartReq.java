package com.ruijing.base.local.upload.web.s3.server.req;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-09-03
 */
public class PartReq implements Serializable {
    
    private static final long serialVersionUID = -2908224607020408538L;
    
    private String ETag;
    private int PartNumber;
    
    @XmlElement(name = "ETag", namespace = "http://s3.amazonaws.com/doc/2006-03-01/")
    public String getETag() {
        return ETag;
    }
    
    public PartReq setETag(String ETag) {
        this.ETag = ETag;
        return this;
    }
    
    @XmlElement(name = "PartNumber", namespace = "http://s3.amazonaws.com/doc/2006-03-01/")
    public int getPartNumber() {
        return PartNumber;
    }
    
    public PartReq setPartNumber(int partNumber) {
        PartNumber = partNumber;
        return this;
    }
    
}
