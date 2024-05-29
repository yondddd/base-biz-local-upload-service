package com.ruijing.base.local.upload.web.s3.controller;

import com.ruijing.base.local.upload.util.s3.S3Util;
import com.ruijing.base.local.upload.web.s3.options.PutBucketOptions;
import com.ruijing.base.local.upload.web.s3.service.BucketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description: bucket controller
 * @Author: WangJieLong
 * @Date: 2024-05-28
 */
@RestController
@RequestMapping("/s3")
public class BucketController {

    @Resource
    private BucketService bucketService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BucketController.class);


    @RequestMapping(value = "/{bucket}/", method = {RequestMethod.PUT})
    public ResponseEntity<String> putBucket(HttpServletRequest httpServerRequest, @PathVariable("bucket") String bucket) throws Exception {
        LOGGER.info("put" + bucket);

        bucketService.putBucket(S3Util.urlDecode(bucket), PutBucketOptions.extractOptions(httpServerRequest));
        return ResponseEntity.ok().build();
    }


    @RequestMapping(value = "/{bucket}/", method = {RequestMethod.DELETE})
    public ResponseEntity<String> delBucket(HttpServletRequest httpServerRequest, @PathVariable("bucket") String bucket) throws Exception {
        LOGGER.info("del" + bucket);
        bucketService.putBucket(S3Util.urlDecode(bucket), PutBucketOptions.extractOptions(httpServerRequest));
        return ResponseEntity.ok().build();
    }

}
