package com.ruijing.base.local.upload.web.biz.service;

import com.ruijing.base.local.upload.web.biz.dto.InitMultipartUploadResultDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: 分片上传接口
 * @Author: WangJieLong
 * @Date: 2023/5/18
 */
public interface MultipartUploadService {
    
    
    /**
     * @param fileName  文件名
     * @param fileMd5   文件md5
     * @param fileSize  文件大小
     * @param totalPart 分片数
     */
    InitMultipartUploadResultDTO initMultipartUpload(String bucketName, String fileName, String fileMd5, Long fileSize, Integer totalPart);
    
    /**
     * @param uploadId 上传id
     * @param partNum  分片id
     * @param partFile 分片文件
     * @return
     */
    String uploadPart(String uploadId, Integer partNum, MultipartFile partFile);
    
    /**
     * 合并分片上传
     *
     * @param uploadId 上传id
     * @return 文件url
     */
    String completeMultipartUpload(String uploadId);
    
    /**
     * 取消分片上传
     *
     * @param uploadId 上传id
     */
    boolean abortMultipartUpload(String uploadId);
    
}
