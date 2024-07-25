package com.ruijing.base.local.upload.web.s3.server.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @see <a href=https://docs.aws.amazon.com/AmazonS3/latest/API/ErrorResponses.html/>
 */
@XmlRootElement(name = "Error")
public class ErrorResponse {
    
    private String code;
    private String message;
    private String requestId;
    private String resource;
    private String hostId;
    
    @XmlElement(name = "Code")
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    @XmlElement(name = "Message")
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    @XmlElement(name = "RequestId")
    public String getRequestId() {
        return requestId;
    }
    
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    
    @XmlElement(name = "HostId")
    public String getHostId() {
        return hostId;
    }
    
    public void setHostId(String hostId) {
        this.hostId = hostId;
    }
    
    @XmlElement(name = "Resource")
    public String getResource() {
        return resource;
    }
    
    public void setResource(String resource) {
        this.resource = resource;
    }
    
}
