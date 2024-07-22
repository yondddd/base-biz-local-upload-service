package com.ruijing.base.local.upload.web.s3.server.controller;

import com.google.common.collect.Lists;
import com.ruijing.base.local.upload.util.s3.S3Util;
import com.ruijing.base.local.upload.web.s3.server.options.PutBucketOptions;
import com.ruijing.base.local.upload.web.s3.server.req.PutBucketReq;
import com.ruijing.base.local.upload.web.s3.server.service.BucketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

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
        PutBucketReq putBucketReq = PutBucketReq.custom()
                .setBucketName(S3Util.urlDecode(bucket))
                .setOptions(PutBucketOptions.extractOptions(httpServerRequest));
        bucketService.putBucket(putBucketReq);
        return ResponseEntity.ok().build();
    }
    
    
    @DeleteMapping(value = "/s3/{bucket}")
    public ResponseEntity<String> delBucket(HttpServletRequest httpServerRequest, @PathVariable("bucket") String bucket) throws Exception {
        LOGGER.info("del" + bucket);
//        bucketService.putBucket(S3Util.urlDecode(bucket), PutBucketOptions.extractOptions(httpServerRequest));
        return ResponseEntity.ok().build();
    }
    
    
    public static void main(String[] args) {
        ArrayList<String> list = Lists.newArrayList("PI", "SP", "Partner", "TDC", "SCHOOL", "EYEAI", "SRM", "OMS", "LAB", "BIO", "EBC", "SRS", "AI", "ECO");
        for (String string : list) {
            String s = String.format("list.add(Pair.of(\"m.rjmart.cn/%s/#/\", \"m.rjmart.cn/%s/\"));", string, string);
            System.out.println(s);
        }
        for (String string : list) {
            String s = String.format("list.add(Pair.of(\"m.test.rj-info.com/%s/#/\", \"m.test.rj-info.com/%s/\"));", string, string);
            System.out.println(s);
        }
    }
    
}
