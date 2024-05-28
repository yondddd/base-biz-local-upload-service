package com.ruijing.base.local.upload.exception;

public class GenericError {
    
    private String bucket;
    private String object;
    private String versionID;
    private Exception err;

    // Constructor
    public GenericError(String bucket, String object, String versionID, Exception err) {
        this.bucket = bucket;
        this.object = object;
        this.versionID = versionID;
        this.err = err;
    }

    // Getters and Setters
    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getVersionID() {
        return versionID;
    }

    public void setVersionID(String versionID) {
        this.versionID = versionID;
    }

    public Exception getErr() {
        return err;
    }

    public void setErr(Exception err) {
        this.err = err;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "GenericError{" +
                "bucket='" + bucket + '\'' +
                ", object='" + object + '\'' +
                ", versionID='" + versionID + '\'' +
                ", err=" + err +
                '}';
    }
}
