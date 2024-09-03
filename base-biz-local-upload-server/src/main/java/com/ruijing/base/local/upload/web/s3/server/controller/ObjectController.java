package com.ruijing.base.local.upload.web.s3.server.controller;

import com.ruijing.base.local.upload.config.BucketDomain;
import com.ruijing.base.local.upload.enums.ApiErrorEnum;
import com.ruijing.base.local.upload.web.s3.server.req.*;
import com.ruijing.base.local.upload.web.s3.server.resp.CompleteMultipartUploadResult;
import com.ruijing.base.local.upload.web.s3.server.resp.InitiateMultipartUploadResult;
import com.ruijing.base.local.upload.web.s3.server.resp.MultiUploadPartResult;
import com.ruijing.base.local.upload.web.s3.server.response.ApiResponseUtil;
import com.ruijing.base.local.upload.web.s3.server.service.ObjectService;
import org.springframework.http.MediaType;
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
import java.nio.file.StandardOpenOption;

/**
 * @Description: object controller
 * @Author: WangJieLong
 * @Date: 2024-05-28
 */
@RestController
public class ObjectController {
    
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
        ObjectGetReq objectGetReq = ObjectGetReq.custom().setBucket(bucket).setKey(fullPath);
        Path path = objectService.getObject(objectGetReq);
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
    
    @DeleteMapping("/s3/{bucket}/**")
    public void deleteObject(@PathVariable String bucket,
                             HttpServletRequest httpServerRequest,
                             HttpServletResponse httpServerResponse) {
        String fullPath = (String) httpServerRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String key = fullPath.replaceAll("/s3/" + bucket + "/", "");
        ObjectDelReq objectDelReq = ObjectDelReq.custom().setBucket(bucket).setKey(key);
        objectService.delObject(objectDelReq);
    }
    
    @PostMapping(value = "/s3/{bucket}/**", params = "uploads")
    public @ResponseBody ResponseEntity<String> createMultipartUpload(@PathVariable String bucket,
                                                                      HttpServletRequest httpServerRequest) {
        String fullPath = (String) httpServerRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String key = fullPath.replaceAll("/s3/" + bucket + "/", "");
        MultipartUploadInitReq req = MultipartUploadInitReq.custom()
                .setBucket(bucket)
                .setKey(key);
        InitiateMultipartUploadResult data = objectService.createMultipartUpload(req);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(ApiResponseUtil.xmlResponse(data));
    }
    
    @PutMapping(value = "/s3/{bucket}/**")
    public @ResponseBody ResponseEntity<String> uploadPart(HttpServletRequest httpServerRequest,
                                                           HttpServletResponse httpServerResponse,
                                                           @PathVariable String bucket,
                                                           @RequestParam Integer partNumber, @RequestParam String uploadId) {
        
        String fullPath = (String) httpServerRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String key = fullPath.replaceAll("/s3/" + bucket + "/", "");
        try {
            MultiUploadPartReq req = MultiUploadPartReq.custom()
                    .setBucket(bucket)
                    .setKey(key)
                    .setUploadId(uploadId)
                    .setPartNum(partNumber)
                    .setInputStream(httpServerRequest.getInputStream());
            MultiUploadPartResult result = objectService.uploadPart(req);
            httpServerResponse.addHeader("ETag", result.geteTag());
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @PostMapping(value = "/s3/{bucket}/**", params = "uploadId")
    public ResponseEntity<String> completeMultipartUpload(HttpServletRequest httpServerRequest,
                                                          @PathVariable String bucket, @RequestParam String uploadId,
                                                          @RequestBody MultipartUploadCompleteRequest body) {
        
        String fullPath = (String) httpServerRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String key = fullPath.replaceAll("/s3/" + bucket + "/", "");
        MultipartUploadCompleteReq req = MultipartUploadCompleteReq.custom()
                .setBucket(bucket)
                .setKey(key)
                .setUploadId(uploadId)
                .setParts(body.getParts());
        CompleteMultipartUploadResult result = objectService.completeMultipartUpload(req);
    }
    
}
