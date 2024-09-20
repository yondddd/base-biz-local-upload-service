package com.ruijing.base.local.upload.web.s3.server.controller;

import com.ruijing.base.local.upload.constant.AmzHeaders;
import com.ruijing.base.local.upload.constant.HttpHeaders;
import com.ruijing.base.local.upload.enums.ApiErrorEnum;
import com.ruijing.base.local.upload.web.s3.metadata.Meta;
import com.ruijing.base.local.upload.web.s3.metadata.Metadata;
import com.ruijing.base.local.upload.web.s3.metadata.Stat;
import com.ruijing.base.local.upload.web.s3.server.async.GetObjectExecutor;
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

/**
 * @Description: object controller
 * @Author: WangJieLong
 * @Date: 2024-05-28
 */
@RestController
public class ObjectController {
    
    @Resource
    private ObjectService objectService;
    
    @PutMapping(value = "/s3/{putBucket}/**")
    public ResponseEntity<Void> putObject(HttpServletRequest httpServerRequest,
                                          @PathVariable("putBucket") String bucket) throws Exception {
        
        String fullPath = (String) httpServerRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String key = fullPath.replaceAll("/s3/" + bucket + "/", "");
        
        String contentLength = httpServerRequest.getHeader(HttpHeaders.CONTENT_LENGTH);
        String contentType = httpServerRequest.getHeader(AmzHeaders.MetaContentType);
        String contentDisposition = httpServerRequest.getHeader(AmzHeaders.MetaContentDisposition);
        Stat stat = Stat.custom().setSize(contentLength);
        Meta meta = Meta.custom().setContentType(contentType).setContentDisposition(contentDisposition);
        ObjectPutReq req = ObjectPutReq.custom().setBucket(bucket)
                .setKey(key)
                .setInputStream(httpServerRequest.getInputStream())
                .setMetadata(Metadata.custom().setMeta(meta).setStat(stat));
        String url = objectService.putObject(req);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{dynamic}/**")
    public void getObject(HttpServletRequest httpServerRequest) {
        
        boolean offer = GetObjectExecutor.offer(httpServerRequest.startAsync());
        if (!offer) {
            ApiResponseUtil.writeError(ApiErrorEnum.ErrBusy);
        }
    }
    
    @DeleteMapping("/s3/{delBucket}/**")
    public void deleteObject(@PathVariable("delBucket") String bucket,
                             HttpServletRequest httpServerRequest,
                             HttpServletResponse httpServerResponse) {
        String fullPath = (String) httpServerRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String key = fullPath.replaceAll("/s3/" + bucket + "/", "");
        ObjectDelReq objectDelReq = ObjectDelReq.custom().setBucket(bucket).setKey(key);
        objectService.delObject(objectDelReq);
    }
    
    @PostMapping(value = "/s3/{createBucket}/**", params = "uploads")
    public ResponseEntity<String> createMultipartUpload(@PathVariable("createBucket") String bucket,
                                                        HttpServletRequest httpServerRequest,
                                                        HttpServletResponse response) {
        String fullPath = (String) httpServerRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String key = fullPath.replaceAll("/s3/" + bucket + "/", "");
        MultipartUploadInitReq req = MultipartUploadInitReq.custom()
                .setBucket(bucket)
                .setKey(key);
        InitiateMultipartUploadResult data = objectService.createMultipartUpload(req);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(ApiResponseUtil.xmlResponse(data));
    }
    
    @PutMapping(value = "/s3/{partBucket}/**", params = {"partNumber", "uploadId"})
    public ResponseEntity<String> uploadPart(HttpServletRequest httpServerRequest,
                                             HttpServletResponse httpServerResponse,
                                             @PathVariable("partBucket") String bucket,
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
    
    @PostMapping(value = "/s3/{completeBucket}/**", params = "uploadId")
    public ResponseEntity<String> completeMultipartUpload(HttpServletRequest httpServerRequest,
                                                          @PathVariable("completeBucket") String bucket,
                                                          @RequestParam String uploadId,
                                                          @RequestBody CompleteMultipartUpload CompleteMultipartUpload) {
        
        String fullPath = (String) httpServerRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String key = fullPath.replaceAll("/s3/" + bucket + "/", "");
        
        MultipartUploadCompleteReq req = MultipartUploadCompleteReq.custom()
                .setBucket(bucket)
                .setKey(key)
                .setUploadId(uploadId)
                .setParts(CompleteMultipartUpload.getParts());
        CompleteMultipartUploadResult data = objectService.completeMultipartUpload(req);
        
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(ApiResponseUtil.xmlResponse(data));
    }
    
    @DeleteMapping(value = "/s3/{abortBucket}/**", params = "uploadId")
    public ResponseEntity<Boolean> abortMultipartUpload(HttpServletRequest httpServerRequest,
                                                        @PathVariable("abortBucket") String bucket, @RequestParam String uploadId) {
        
        String fullPath = (String) httpServerRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String key = fullPath.replaceAll("/s3/" + bucket + "/", "");
        MultipartUploadAbortReq req = MultipartUploadAbortReq.custom()
                .setBucket(bucket)
                .setKey(key)
                .setUploadId(uploadId);
        objectService.abortMultipartUpload(req);
        return ResponseEntity.ok().build();
    }
    
}
