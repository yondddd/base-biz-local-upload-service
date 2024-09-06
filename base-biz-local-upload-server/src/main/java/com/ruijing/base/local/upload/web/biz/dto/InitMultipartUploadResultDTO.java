package com.ruijing.base.local.upload.web.biz.dto;

import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModelProperty;

import java.io.Serializable;

/**
 * @Description: 分片上传初始化结果
 * @Author: WangJieLong
 * @Date: 2023/5/18
 */
public class InitMultipartUploadResultDTO implements Serializable {
    
    private static final long serialVersionUID = -4018495737531782571L;
    
    @RpcModelProperty(value = "上传id", required = true)
    private String uploadId;
    @RpcModelProperty(value = "文件总分片", required = true)
    private Integer totalPart;
    @RpcModelProperty(value = "文件分片大小", required = true)
    private Long partSize;
    @RpcModelProperty(value = "当前上传分片序号，第一次上传时为1", required = true)
    private Integer currentPartNum;
    @RpcModelProperty(value = "根据上传文件md5查找，若文件曾经上传过，则直接返回文件url。此字段有值代表上传成功，无需继续分片上传")
    private String fileUrl;
    
    public String getUploadId() {
        return uploadId;
    }
    
    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }
    
    public Integer getTotalPart() {
        return totalPart;
    }
    
    public void setTotalPart(Integer totalPart) {
        this.totalPart = totalPart;
    }
    
    public Long getPartSize() {
        return partSize;
    }
    
    public void setPartSize(Long partSize) {
        this.partSize = partSize;
    }
    
    public Integer getCurrentPartNum() {
        return currentPartNum;
    }
    
    public void setCurrentPartNum(Integer currentPartNum) {
        this.currentPartNum = currentPartNum;
    }
    
    public String getFileUrl() {
        return fileUrl;
    }
    
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    
    @Override
    public String toString() {
        return "InitMultipartUploadResultDTO{" +
                "uploadId='" + uploadId + '\'' +
                ", totalChunk=" + totalPart +
                ", chunkSize=" + partSize +
                ", currentChunk=" + currentPartNum +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }
}
