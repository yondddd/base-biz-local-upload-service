package com.ruijing.base.local.upload.web.s3.server.controller;

import com.ruijing.base.local.upload.web.s3.server.req.PutObjectReq;
import com.ruijing.base.local.upload.web.s3.server.service.ObjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: object controller
 * @Author: WangJieLong
 * @Date: 2024-05-28
 */
@RestController
public class ObjectController {
    
    private final ObjectService objectService;
    
    public ObjectController(ObjectService objectService) {
        this.objectService = objectService;
    }
    
    @PutMapping(value = "/s3/{bucket}/{*objectName}")
    public @ResponseBody ResponseEntity<String> putObject(HttpServletRequest httpServerRequest,
                                                          @PathVariable("bucket") String bucket,
                                                          @PathVariable("objectName") String objectName) throws Exception {
        
        PutObjectReq req = PutObjectReq.custom().setBucketName(bucket)
                .setObjectName(objectName);
        String url = objectService.putObject(req);
        return ResponseEntity.ok().build();
    }
    
}
