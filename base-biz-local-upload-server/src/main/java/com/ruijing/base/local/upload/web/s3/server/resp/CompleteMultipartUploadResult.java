package com.ruijing.base.local.upload.web.s3.server.resp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CompleteMultipartUploadResult")
public class CompleteMultipartUploadResult {
    
    private String location;
    private String bucket;
    private String key;
    private String eTag;
    
    @XmlElement(name = "Location")
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    @XmlElement(name = "Bucket")
    public String getBucket() {
        return bucket;
    }
    
    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
    
    @XmlElement(name = "Key")
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    @XmlElement(name = "ETag")
    public String getETag() {
        return eTag;
    }
    
    public void setETag(String eTag) {
        this.eTag = eTag;
    }
    
}
