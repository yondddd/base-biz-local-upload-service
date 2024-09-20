package com.ruijing.base.local.upload.web.biz.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件分片上传
 *
 * @TableName file_chunk_upload
 */
public class FileChunkUploadDO implements Serializable {
    
    /**
     * oss返回的uploadId
     */
    private String ossUploadId;
    
    private String fileName;
    
    /**
     * 文件md5值
     */
    private String fileMd5;
    
    /**
     * 文件url
     */
    private String fileUrl;
    
    /**
     * 所属bucket名称
     */
    private String bucketName;
    
    /**
     * 阿里云oss的objectName
     */
    private String objectName;
    
    /**
     * 文件总大小
     */
    private Long totalSize;
    
    /**
     * 总共多少片
     */
    private Integer totalPart;
    
    /**
     * 当前分片序号
     */
    private Integer currentPartNum;
    
    /**
     * 存放json,阿里云oss返回的,合成文件需要使用
     */
    private String partEtag;
    
    /**
     * 状态 1初始化完成 2上传中 3上传完成 4取消
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
    private static final long serialVersionUID = 1L;
    
    /**
     * oss返回的uploadId
     */
    public String getOssUploadId() {
        return ossUploadId;
    }
    
    /**
     * oss返回的uploadId
     */
    public void setOssUploadId(String ossUploadId) {
        this.ossUploadId = ossUploadId;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public FileChunkUploadDO setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }
    
    /**
     * 文件md5值
     */
    public String getFileMd5() {
        return fileMd5;
    }
    
    /**
     * 文件md5值
     */
    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }
    
    public String getFileUrl() {
        return fileUrl;
    }
    
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    
    public String getBucketName() {
        return bucketName;
    }
    
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
    
    /**
     * 阿里云oss的objectName
     */
    public String getObjectName() {
        return objectName;
    }
    
    /**
     * 阿里云oss的objectName
     */
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
    
    /**
     * 文件总大小
     */
    public Long getTotalSize() {
        return totalSize;
    }
    
    /**
     * 文件总大小
     */
    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }
    
    /**
     * 总共多少片
     */
    public Integer getTotalPart() {
        return totalPart;
    }
    
    /**
     * 总共多少片
     */
    public void setTotalPart(Integer totalPart) {
        this.totalPart = totalPart;
    }
    
    /**
     * 当前分片序号
     */
    public Integer getCurrentPartNum() {
        return currentPartNum;
    }
    
    /**
     * 当前分片序号
     */
    public void setCurrentPartNum(Integer currentPartNum) {
        this.currentPartNum = currentPartNum;
    }
    
    /**
     * 存放json,阿里云oss返回的,合成文件需要使用
     */
    public String getPartEtag() {
        return partEtag;
    }
    
    /**
     * 存放json,阿里云oss返回的,合成文件需要使用
     */
    public void setPartEtag(String partEtag) {
        this.partEtag = partEtag;
    }
    
    /**
     * 状态 1初始化完成 2上传中 3上传完成 4取消
     */
    public Integer getStatus() {
        return status;
    }
    
    /**
     * 状态 1初始化完成 2上传中 3上传完成 4取消
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }
    
    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    /**
     * 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }
    
    /**
     * 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    @Override
    public String toString() {
        return "FileChunkUploadDO{" +
                "ossUploadId='" + ossUploadId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileMd5='" + fileMd5 + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", objectName='" + objectName + '\'' +
                ", totalSize=" + totalSize +
                ", totalPart=" + totalPart +
                ", currentPartNum=" + currentPartNum +
                ", partEtag='" + partEtag + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}