package com.ruijing.base.local.upload.web.s3.controller;

import com.ruijing.base.local.upload.util.s3.S3Util;
import com.ruijing.base.local.upload.web.s3.options.PutBucketOptions;
import com.ruijing.base.local.upload.web.s3.service.BucketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        bucketService.putBucket(S3Util.urlDecode(bucket), PutBucketOptions.extractOptions(httpServerRequest));
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/s3/{bucket}")
    public ResponseEntity<String> delBucket(HttpServletRequest httpServerRequest, @PathVariable("bucket") String bucket) throws Exception {
        LOGGER.info("del" + bucket);
        bucketService.putBucket(S3Util.urlDecode(bucket), PutBucketOptions.extractOptions(httpServerRequest));
        return ResponseEntity.ok().build();
    }

}
