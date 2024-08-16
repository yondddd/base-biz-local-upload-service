package com.ruijing.base.local.upload.web.biz.resp;

import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModel;
import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModelProperty;

import java.io.Serializable;

/**
 * @Description: 初始化分片上传结果
 * @Author: WangJieLong
 * @Date: 2023/5/18
 */
@RpcModel("初始化分片上传结果")
public class InitMultipartUploadResp implements Serializable {
    
    private static final long serialVersionUID = -1382739702977931415L;
    
    @RpcModelProperty(value = "上传id,(后续分片上传、合并分片时携带，做此次上传的唯一标识)", required = true)
    private String uploadId;
    @RpcModelProperty(value = "根据上传文件md5查找，若文件曾经上传过，则直接返回文件url。此字段有值代表上传成功，无需继续分片上传")
    private String fileUrl;
    
    
    public String getUploadId() {
        return uploadId;
    }
    
    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }
    
    public String getFileUrl() {
        return fileUrl;
    }
    
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    
    @Override
    public String toString() {
        return "InitMultipartUploadResp{" +
                "uploadId='" + uploadId + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }
    
}
