package com.ruijing.base.local.upload.web.s3.server.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "CompleteMultipartUpload")
public class MultipartUploadCompleteRequest {
    
    private List<PartReq> parts;
    
    @XmlElement(name = "Part")
    public List<PartReq> getParts() {
        return parts;
    }
    
    public void setParts(List<PartReq> parts) {
        this.parts = parts;
    }
    
    
}
