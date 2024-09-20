package com.ruijing.base.local.upload.web.s3.server.service.impl;

import com.ruijing.base.local.upload.constant.SysConstant;
import com.ruijing.base.local.upload.util.EncryptUtil;
import com.ruijing.base.local.upload.util.UUIDUtil;
import com.ruijing.base.local.upload.web.biz.dto.FileChunkUploadDO;
import com.ruijing.base.local.upload.web.biz.mapper.FileChunkUploadMapper;
import com.ruijing.base.local.upload.web.s3.metadata.Meta;
import com.ruijing.base.local.upload.web.s3.metadata.Metadata;
import com.ruijing.base.local.upload.web.s3.metadata.Stat;
import com.ruijing.base.local.upload.web.s3.metadata.mime.Mimetypes;
import com.ruijing.base.local.upload.web.s3.server.req.*;
import com.ruijing.base.local.upload.web.s3.server.resp.CompleteMultipartUploadResult;
import com.ruijing.base.local.upload.web.s3.server.resp.InitiateMultipartUploadResult;
import com.ruijing.base.local.upload.web.s3.server.resp.MultiUploadPartResult;
import com.ruijing.base.local.upload.web.s3.server.service.ObjectService;
import com.ruijing.fundamental.common.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Comparator;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-07-25
 */
@Service
public class ObjectServiceImpl implements ObjectService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectServiceImpl.class);
    
    @Override
    public String putObject(ObjectPutReq req) {
        Metadata metadata = req.getMetadata();
        String bucket = "/" + SysConstant.dataPath + req.getBucket();
        String key = bucket + "/" + req.getKey();
        String file = key + "/part.1";
        String json = key + "/xl.json";
        Path filePath = Paths.get(file);
        try {
            Path parentDir = filePath.getParent();
            if (parentDir != null) {
                Files.createDirectories(parentDir);
            }
            Files.copy(req.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            metadata.getStat().setLastModified(String.valueOf(System.currentTimeMillis()));
            Path metaPath = Paths.get(json);
            // write metadata
            Files.write(metaPath, JsonUtils.toJson(metadata).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return key;
    }
    
    @Override
    public Pair<Path, Path> getObject(ObjectGetReq req) {
        String key = "/" + SysConstant.dataPath + req.getBucket() + req.getKey();
        String file = key + "/part.1";
        String metadata = key + "/xl.json";
        return Pair.of(Paths.get(file), Paths.get(metadata));
    }
    
    @Override
    public void delObject(ObjectDelReq req) {
        String key = "/" + SysConstant.dataPath + req.getBucket() + req.getKey();
        Path path = Paths.get(key);
        if (!Files.exists(path)) {
            LOGGER.error("<|>ObjectServiceImpl_delObject<|>req:{}<|>", JsonUtils.toJson(req));
            return;
        }
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public InitiateMultipartUploadResult createMultipartUpload(MultipartUploadInitReq req) {
        String uploadId = UUIDUtil.generateId();
        String tempPath = SysConstant.tempPath + "/" + uploadId + "/";
        Path path = Paths.get(tempPath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return InitiateMultipartUploadResult.custom()
                .setBucket(req.getBucket())
                .setKey(req.getKey())
                .setUploadId(uploadId);
    }
    
    @Override
    public MultiUploadPartResult uploadPart(MultiUploadPartReq req) {
        String temp = SysConstant.tempPath + "/" + req.getUploadId() + "/";
        Path tempPath = Paths.get(temp);
        if (!Files.exists(tempPath)) {
            throw new RuntimeException("uploadId不存在" + req.getUploadId());
        }
        MultiUploadPartResult result = new MultiUploadPartResult();
        result.setPartNum(req.getPartNum().toString());
        String part = temp + req.getPartNum() + ".part";
        try {
            String etag = EncryptUtil.generateETag(part);
            result.seteTag(etag);
            Files.copy(req.getInputStream(), Paths.get(part), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    
    @Override
    public CompleteMultipartUploadResult completeMultipartUpload(MultipartUploadCompleteReq req) {
        Metadata metadata = this.getMetadataByUploadId(req.getUploadId());
        String temp = SysConstant.tempPath + "/" + req.getUploadId() + "/";
        String key = "/" + SysConstant.dataPath + req.getBucket() + "/" + req.getKey();
        // 怎么知道有没有漏?
        boolean check = true;
        for (PartReq part : req.getParts()) {
            String p = temp + part.getPartNumber() + ".part";
            try {
                if (!part.getETag().equals(EncryptUtil.generateETag(p))) {
                    check = false;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (!check) {
            throw new RuntimeException("check fail");
        }
        req.getParts().sort(Comparator.comparing(PartReq::getPartNumber));
        // merge
        String file = key + "/part.1";
        String json = key + "/xl.json";
        Path filePath = Paths.get(file);
        try {
            Path parentDir = filePath.getParent();
            if (parentDir != null) {
                Files.createDirectories(parentDir);
            }
            // 文件合并
            try (FileChannel outputChannel = FileChannel.open(filePath, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
                for (PartReq part : req.getParts()) {
                    String partFilePath = temp + part.getPartNumber() + ".part";
                    Path partPath = Paths.get(partFilePath);
                    try (FileChannel partChannel = FileChannel.open(partPath, StandardOpenOption.READ)) {
                        partChannel.transferTo(0, partChannel.size(), outputChannel);
                    }
                }
            }
            // 元数据
            metadata.getStat().setLastModified(String.valueOf(System.currentTimeMillis()));
            Path metaPath = Paths.get(json);
            // write metadata
            Files.write(metaPath, JsonUtils.toJson(metadata).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CompleteMultipartUploadResult result = new CompleteMultipartUploadResult();
        result.setBucket(req.getBucket());
        result.setKey(req.getKey());
        try {
            result.setETag(EncryptUtil.generateETag(req.getBucket() + "/" + req.getKey()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        result.setLocation("test");
        return result;
    }
    
    private Metadata getMetadataByUploadId(String uploadId) {
        FileChunkUploadDO exist = FileChunkUploadMapper.getByUploadId(uploadId);
        String fileName = exist.getFileName();
        String contentType = Mimetypes.getInstance().getMimetype(fileName, exist.getObjectName());
        try {
            fileName = StringUtils.replace(URLEncoder.encode(fileName, "UTF-8"), "+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String disposition = "filename=" + fileName + ";filename*=" + fileName;
        Stat stat = Stat.custom().setSize(exist.getTotalSize().toString());
        Meta meta = Meta.custom().setContentType(contentType).setContentDisposition(disposition);
        return Metadata.custom().setMeta(meta).setStat(stat);
    }
    
    @Override
    public void abortMultipartUpload(MultipartUploadAbortReq req) {
        // abort 直接删掉tem 里的uploadId 目录
    }
    
}
