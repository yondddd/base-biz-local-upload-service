package com.ruijing.base.local.upload.web.s3.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Error")
public class ErrorResponse {

    private String code;
    private String message;
    private String requestId;
    private String hostId;
    private String key;
    private String ec;

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

    @XmlElement(name = "Key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @XmlElement(name = "EC")
    public String getEc() {
        return ec;
    }

    public void setEc(String ec) {
        this.ec = ec;
    }

}
