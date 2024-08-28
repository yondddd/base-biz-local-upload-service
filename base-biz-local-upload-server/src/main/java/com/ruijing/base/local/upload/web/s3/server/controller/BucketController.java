package com.ruijing.base.local.upload.web.s3.server.controller;

import com.ruijing.base.local.upload.util.s3.S3Util;
import com.ruijing.base.local.upload.web.s3.server.options.PutBucketOptions;
import com.ruijing.base.local.upload.web.s3.server.req.BucketDelReq;
import com.ruijing.base.local.upload.web.s3.server.req.BucketPutReq;
import com.ruijing.base.local.upload.web.s3.server.resp.ListAllMyBucketsResult;
import com.ruijing.base.local.upload.web.s3.server.response.ApiResponseUtil;
import com.ruijing.base.local.upload.web.s3.server.service.BucketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: bucket controller
 * @Author: WangJieLong
 * @Date: 2024-05-28
 */
@RestController
public class BucketController {
    
    private final BucketService bucketService;
    
    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BucketController.class);
    
    @PutMapping(value = "/s3/{bucket}")
    public @ResponseBody ResponseEntity<String> putBucket(HttpServletRequest httpServerRequest, @PathVariable("bucket") String bucket) throws Exception {
        LOGGER.info("putBucket:{}", bucket);
        // todo action鉴权、iam鉴权
        BucketPutReq putBucketReq = BucketPutReq.custom()
                .setBucketName(S3Util.urlDecode(bucket))
                .setOptions(PutBucketOptions.extractOptions(httpServerRequest));
        bucketService.putBucket(putBucketReq);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping(value = "/s3/{bucket}")
    public ResponseEntity<String> delBucket(HttpServletRequest httpServerRequest, @PathVariable("bucket") String bucket) throws Exception {
        LOGGER.info("delBucket:{}", bucket);
        BucketDelReq delBucketReq = BucketDelReq.custom()
                .setBucketName(S3Util.urlDecode(bucket));
        bucketService.deleteBucket(delBucketReq);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/s3/")
    public @ResponseBody ResponseEntity<String> listBuckets() {
        ListAllMyBucketsResult data = bucketService.listBuckets();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(ApiResponseUtil.xmlResponse(data));
    }
    
}
