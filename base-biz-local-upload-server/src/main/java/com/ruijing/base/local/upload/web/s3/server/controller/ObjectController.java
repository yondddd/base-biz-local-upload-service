package com.ruijing.base.local.upload.web.s3.server.controller;

import com.ruijing.base.local.upload.config.BucketDomain;
import com.ruijing.base.local.upload.config.SystemConfig;
import com.ruijing.base.local.upload.enums.ApiErrorEnum;
import com.ruijing.base.local.upload.web.s3.server.req.ObjectPutReq;
import com.ruijing.base.local.upload.web.s3.server.response.ApiResponseUtil;
import com.ruijing.base.local.upload.web.s3.server.service.ObjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @Description: object controller
 * @Author: WangJieLong
 * @Date: 2024-05-28
 */
@RestController
public class ObjectController {
    
    @Resource
    private SystemConfig systemConfig;
    @Resource
    private ObjectService objectService;
    
    
    @PutMapping(value = "/s3/{bucket}/{objectName}")
    public @ResponseBody ResponseEntity<String> putObject(HttpServletRequest httpServerRequest,
                                                          @PathVariable("bucket") String bucket,
                                                          @PathVariable("objectName") String objectName) throws Exception {
        
        ObjectPutReq req = ObjectPutReq.custom().setBucketName(bucket)
                .setObjectName(objectName)
                .setInputStream(httpServerRequest.getInputStream());
        String url = objectService.putObject(req);
        return ResponseEntity.ok(url);
    }
    
    // todo 应该由域名映射bucket 去加上目录查
    // 非阻塞
    @GetMapping("/{dynamic}/**")
    public void getObject(HttpServletRequest httpServerRequest,
                          HttpServletResponse httpServerResponse) {
        
        String serverName = httpServerRequest.getServerName();
        String bucket = BucketDomain.getBucket(serverName);
        if (bucket == null) {
            ApiResponseUtil.writeError(ApiErrorEnum.ErrNoSuchKey);
            return;
        }
        httpServerRequest.getContextPath();
        String fullPath = (String) httpServerRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String key = "/" + systemConfig.getDataPath() + bucket + fullPath;
        Path path = Paths.get(key);
        if (!Files.exists(path)) {
            ApiResponseUtil.writeError(ApiErrorEnum.ErrNoSuchKey);
            return;
        }
        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ)) {
            httpServerResponse.setContentType(Files.probeContentType(path));
            long fileSize = Files.size(path);
            fileChannel.transferTo(0, fileSize, Channels.newChannel(httpServerResponse.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @DeleteMapping("/s3/{dynamicPath}/**")
    public void deleteObject(HttpServletRequest httpServerRequest,
                             HttpServletResponse httpServerResponse) {
        String fullPath = (String) httpServerRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        fullPath = fullPath.replaceAll("/s3/", "");
        String key = "/" + systemConfig.getDataPath() + "/" + fullPath;
        Path path = Paths.get(key);
        
        if (!Files.exists(path)) {
            ApiResponseUtil.writeError(ApiErrorEnum.ErrNoSuchKey);
            return;
        }
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    // 多key delete
    
    // 分片上传
    @PostMapping(value = "/s3/{objectName}/**", params = "uploads")
    public @ResponseBody ResponseEntity<String> createMultipartUpload(HttpServletRequest httpServerRequest) {
        // tem
        String fullPath = (String) httpServerRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        fullPath = fullPath.replaceAll("/s3/", "");
        String key = "/" + systemConfig.getDataPath() + "/" + fullPath;
//        objectService.createMultipartUpload();
//        InitiateMultipartUploadResult data = bucketService.listBuckets();
//        return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(ApiResponseUtil.xmlResponse(data));
        return null;
    }
    
}
