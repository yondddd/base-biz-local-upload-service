package com.ruijing.base.local.upload.s3.controller;

import com.ruijing.base.local.upload.s3.options.PutBucketOptions;
import com.ruijing.base.local.upload.s3.service.BucketService;
import com.ruijing.base.local.upload.util.s3.S3Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description: bucket controller
 * @Author: WangJieLong
 * @Date: 2024-05-28
 */
@RestController
public class BucketController {

    @Resource
    private BucketService bucketService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BucketController.class);

    // 每天都看一看

    @PutMapping("/s3/{putBucket}/")
    public ResponseEntity<String> putBucket(@PathVariable String putBucket, HttpServletRequest httpServerRequest) throws Exception {
        bucketService.putBucket(S3Util.urlDecode(putBucket), PutBucketOptions.extractOptions(httpServerRequest));
        return ResponseEntity.ok().build();
    }


}
