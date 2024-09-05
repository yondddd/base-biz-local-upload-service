package com.ruijing.base.local.upload.web.s3.metadata;


/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-09-05
 */
public class Meta {
    
    private String contentType;
    private String etag;
    private String contentDisposition;
    
    public static Meta custom() {
        return new Meta();
    }
    
    public String getContentType() {
        return contentType;
    }
    
    public Meta setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }
    
    public String getEtag() {
        return etag;
    }
    
    public Meta setEtag(String etag) {
        this.etag = etag;
        return this;
    }
    
    public String getContentDisposition() {
        return contentDisposition;
    }
    
    public Meta setContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
        return this;
    }
    
    @Override
    public String toString() {
        return "Meta{" +
                "contentType='" + contentType + '\'' +
                ", etag='" + etag + '\'' +
                ", contentDisposition='" + contentDisposition + '\'' +
                '}';
    }
}
