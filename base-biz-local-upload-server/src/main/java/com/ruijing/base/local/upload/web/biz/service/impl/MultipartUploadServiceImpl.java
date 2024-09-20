package com.ruijing.base.local.upload.web.biz.service.impl;

import com.ruijing.base.local.upload.config.BucketDomain;
import com.ruijing.base.local.upload.util.PathUtils;
import com.ruijing.base.local.upload.web.biz.dto.FileChunkUploadDO;
import com.ruijing.base.local.upload.web.biz.dto.InitMultipartUploadResultDTO;
import com.ruijing.base.local.upload.web.biz.dto.PartDTO;
import com.ruijing.base.local.upload.web.biz.enums.FilePartUploadStatusEnum;
import com.ruijing.base.local.upload.web.biz.mapper.FileChunkUploadMapper;
import com.ruijing.base.local.upload.web.biz.service.MultipartUploadService;
import com.ruijing.base.local.upload.web.s3.client.BaseS3Client;
import com.ruijing.fundamental.common.util.JsonUtils;
import com.ruijing.fundamental.lang.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.UploadPartResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description: 分片上传实现
 * @Author: WangJieLong
 * @Date: 2023/5/18
 */
@Service
public class MultipartUploadServiceImpl implements MultipartUploadService {
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MultipartUploadServiceImpl.class);
    
    @Override
    public InitMultipartUploadResultDTO initMultipartUpload(String bucketName, String fileName, String fileMd5, Long fileSize, Integer totalPart) {
        
        CreateMultipartUploadResponse multipartUpload = BaseS3Client.createMultipartUpload(bucketName, fileName);
        
        String uploadId = multipartUpload.uploadId();
        String key = multipartUpload.key();
        FileChunkUploadDO insert = new FileChunkUploadDO();
        insert.setOssUploadId(uploadId);
        insert.setFileName(fileName);
        insert.setFileMd5(fileMd5);
        insert.setObjectName(key);
        insert.setTotalSize(fileSize);
        insert.setTotalPart(totalPart);
        insert.setCurrentPartNum(0);
        insert.setStatus(FilePartUploadStatusEnum.INIT_FINISH.getVal());
        insert.setPartEtag("[]");
        insert.setFileUrl(PathUtils.buildPath(BucketDomain.getDomain(bucketName), key));
        insert.setBucketName(bucketName);
        // save
        FileChunkUploadMapper.write(insert);
        InitMultipartUploadResultDTO result = new InitMultipartUploadResultDTO();
        result.setUploadId(uploadId);
        result.setTotalPart(totalPart);
        result.setCurrentPartNum(1);
        return result;
    }
    
    @Override
    public String uploadPart(String uploadId, Integer partNum, MultipartFile partFile) {
        Preconditions.notNull(uploadId, "上传id为空");
        Preconditions.notNull(partNum, "分片id为空");
        Preconditions.notNull(partFile, "分片文件为空");
        FileChunkUploadDO exist = FileChunkUploadMapper.getByUploadId(uploadId);
        Preconditions.notNull(exist, "上传id查询为空:" + uploadId);
        if (FilePartUploadStatusEnum.INIT_FINISH.getVal().equals(exist.getStatus())) {
            exist.setStatus(FilePartUploadStatusEnum.UPLOADING.getVal());
            FileChunkUploadMapper.write(exist);
        }
        List<PartDTO> parteTags = JsonUtils.parseList(exist.getPartEtag(), PartDTO.class);
        Map<Integer, PartDTO> collect =
                parteTags.stream().collect(Collectors.toMap(PartDTO::getPartNumber, Function.identity(), (key1, key2) -> key1));
        PartDTO partExist = collect.get(partNum);
        // 如果存在就忽略
        if (partExist != null) {
            LOGGER.info("<|>MultipartUploadServiceImpl_uploadPart_partExist<|>uploadId:{}<|>partNum:{}<|>", uploadId, partNum);
            return partExist.geteTag();
        }
        // 允许不按顺序并发上传
        try {
            UploadPartResponse response = BaseS3Client.uploadPart(uploadId, exist.getBucketName(), exist.getObjectName(), partNum, partFile.getInputStream(), partFile.getSize());
            PartDTO dto = new PartDTO();
            dto.seteTag(response.eTag());
            dto.setPartNumber(partNum);
            return this.updateLocalPart(exist.getOssUploadId(), dto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    @Override
    public String completeMultipartUpload(String uploadId) {
        Preconditions.notNull(uploadId, "上传id为空");
        FileChunkUploadDO exist = FileChunkUploadMapper.getByUploadId(uploadId);
        Integer uploadStatus = exist.getStatus();
        Preconditions.isTrue(FilePartUploadStatusEnum.UPLOADING.getVal().equals(uploadStatus), "上传状态错误:" + uploadStatus);
        List<PartDTO> parteTags = JsonUtils.parseList(exist.getPartEtag(), PartDTO.class);
        Preconditions.isTrue(exist.getTotalPart().equals(parteTags.size()), "分片数量:" + parteTags.size() + "不等于" + exist.getTotalPart());
        List<CompletedPart> parts = new ArrayList<>();
        for (PartDTO parteTag : parteTags) {
            CompletedPart item = CompletedPart.builder().eTag(parteTag.geteTag())
                    .partNumber(parteTag.getPartNumber()).build();
            parts.add(item);
        }
        BaseS3Client.completeMultipartUpload(uploadId, exist.getBucketName(), exist.getObjectName(), parts);
        exist.setStatus(FilePartUploadStatusEnum.FINISH.getVal());
        FileChunkUploadMapper.write(exist);
        return exist.getFileUrl();
    }
    
    @Override
    public boolean abortMultipartUpload(String uploadId) {
        Preconditions.notNull(uploadId, "上传id为空");
        FileChunkUploadDO exist = FileChunkUploadMapper.getByUploadId(uploadId);
        Integer uploadStatus = exist.getStatus();
        Preconditions.isTrue(FilePartUploadStatusEnum.INIT_FINISH.getVal().equals(uploadStatus)
                || FilePartUploadStatusEnum.UPLOADING.getVal().equals(uploadStatus), "取消失败,上传状态错误:" + uploadStatus);
        try {
            BaseS3Client.abortMultipartUpload(uploadId, exist.getBucketName(), exist.getObjectName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        exist.setStatus(FilePartUploadStatusEnum.ABORT.getVal());
        FileChunkUploadMapper.write(exist);
        return true;
    }
    
    
    /**
     * 更新本地分片记录
     *
     * @param uploadId 主键id
     * @param parteTag 分片数据
     */
    private synchronized String updateLocalPart(String uploadId, PartDTO parteTag) {
        FileChunkUploadDO old = FileChunkUploadMapper.getByUploadId(uploadId);
        List<PartDTO> partETags = JsonUtils.parseList(old.getPartEtag(), PartDTO.class);
        Set<Integer> existEtag = partETags.stream().map(PartDTO::getPartNumber).collect(Collectors.toSet());
        // 忽略重复上传
        if (existEtag.contains(parteTag.getPartNumber())) {
            return parteTag.geteTag();
        }
        partETags.add(parteTag);
        String json = JsonUtils.toJson(partETags);
        old.setCurrentPartNum(partETags.size());
        old.setPartEtag(json);
        FileChunkUploadMapper.write(old);
        return parteTag.geteTag();
    }
    
}
