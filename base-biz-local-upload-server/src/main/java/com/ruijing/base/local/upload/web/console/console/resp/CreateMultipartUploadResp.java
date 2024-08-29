package com.ruijing.base.local.upload.web.console.console.resp;

import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModelProperty;

import java.io.Serializable;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-08-29
 */
public class CreateMultipartUploadResp implements Serializable {
    
    private static final long serialVersionUID = 1511105970285461035L;
    
    @RpcModelProperty(value = "上传id,(后续分片上传、合并分片时携带，做此次上传的唯一标识)", required = true)
    private String uploadId;
    @RpcModelProperty(value = "根据上传文件md5查找，若文件曾经上传过，则直接返回文件url。此字段有值代表上传成功，无需继续分片上传")
    private String fileUrl;
    
    public String getUploadId() {
        return uploadId;
    }
    
    public CreateMultipartUploadResp setUploadId(String uploadId) {
        this.uploadId = uploadId;
        return this;
    }
    
    public String getFileUrl() {
        return fileUrl;
    }
    
    public CreateMultipartUploadResp setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
        return this;
    }
    
    @Override
    public String toString() {
        return "CreateMultipartUploadResp{" +
                "uploadId='" + uploadId + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }
}
