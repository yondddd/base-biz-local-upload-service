package com.ruijing.base.local.upload.web.business.req;

import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModel;
import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModelProperty;

import java.io.Serializable;

/**
 * @Description: 初始化分片上传请求
 * @Author: WangJieLong
 * @Date: 2023/5/18
 */
@RpcModel("初始化分片上传请求")
public class InitMultipartUploadRequest implements Serializable {
    
    private static final long serialVersionUID = 2374642341496626229L;
    
    @RpcModelProperty(value = "文件账号id", required = true)
    private String id;
    
    @RpcModelProperty(value = "文件名称(带文件后缀)", required = true)
    private String fileName;
    
    @RpcModelProperty(value = "文件md5(前端生成),同一文件生成的md5需相同", required = true)
    private String fileMd5;
    
    @RpcModelProperty(value = "文件总大小(字节数)", required = true)
    private Long fileSize;
    
    @RpcModelProperty(value = "文件分片数量", required = true)
    private Integer totalPart;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public String getFileMd5() {
        return fileMd5;
    }
    
    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }
    
    public Long getFileSize() {
        return fileSize;
    }
    
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
    
    public Integer getTotalPart() {
        return totalPart;
    }
    
    public void setTotalPart(Integer totalPart) {
        this.totalPart = totalPart;
    }
    
    @Override
    public String toString() {
        return "InitMultipartUploadRequest{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileMd5='" + fileMd5 + '\'' +
                ", fileSize=" + fileSize +
                ", totalPart=" + totalPart +
                '}';
    }
}
