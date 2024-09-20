package com.ruijing.base.local.upload.web.s3.server.req;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "CompleteMultipartUpload", namespace = "http://s3.amazonaws.com/doc/2006-03-01/")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompleteMultipartUpload implements Serializable {
    
    private static final long serialVersionUID = 7365356253268774415L;
    
    @XmlElement(name = "Part", namespace = "http://s3.amazonaws.com/doc/2006-03-01/")
    private List<PartReq> parts;
    
    public List<PartReq> getParts() {
        return parts;
    }
    
    public CompleteMultipartUpload setParts(List<PartReq> parts) {
        this.parts = parts;
        return this;
    }
    
}
