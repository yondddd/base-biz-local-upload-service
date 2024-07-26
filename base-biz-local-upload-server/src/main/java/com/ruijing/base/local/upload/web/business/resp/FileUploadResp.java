package com.ruijing.base.local.upload.web.business.resp;

public class FileUploadResp {
    //0000为成功
    private String code = "0000";
    //错误消息
    private String msg = "成功";
    //带host的路径
    private String absolutePath;
    //桶，暂时没有
    private String bucket;
    //不带host的路径，暂不返回
    private String relativePath;
    
    public boolean isSuccess() {
        return "0000".equals(code);
    }
    
    public static FileUploadResp failed(String msg) {
        FileUploadResp resp = new FileUploadResp();
        resp.setCode("0001");
        resp.setMsg(msg);
        return resp;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public String getAbsolutePath() {
        return absolutePath;
    }
    
    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }
    
    public String getBucket() {
        return bucket;
    }
    
    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
    
    public String getRelativePath() {
        return relativePath;
    }
    
    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }
}
