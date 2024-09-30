package com.ruijing.base.local.upload.web.console.console.controller;

import com.ruijing.base.local.upload.web.console.console.req.BucketCreateReq;
import com.ruijing.base.local.upload.web.console.console.req.BucketDelReq;
import com.ruijing.base.local.upload.web.console.console.resp.BucketVO;
import com.ruijing.base.local.upload.web.s3.client.BaseS3Client;
import com.ruijing.fundamental.api.remote.RemoteResponse;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.model.Bucket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-09-23
 */
@RestController
@RequestMapping("/console/bucket")
public class ConsoleBucketController {
    
    @PostMapping("/listBucket")
    @ResponseBody
    public RemoteResponse<List<BucketVO>> listBucket() {
        List<Bucket> buckets = BaseS3Client.listBuckets();
        List<BucketVO> data = new ArrayList<>();
        for (Bucket bucket : buckets) {
            BucketVO item = new BucketVO();
            item.setName(bucket.name());
            item.setCreationDate(Date.from(bucket.creationDate()));
            data.add(item);
        }
        return RemoteResponse.success(data);
    }
    
    @PostMapping("/putBucket")
    @ResponseBody
    public RemoteResponse<Boolean> putBucket(@RequestBody BucketCreateReq req) {
        
        BaseS3Client.createBucket(req.getBucket());
        return RemoteResponse.success();
    }
    
    @PostMapping("/delBucket")
    @ResponseBody
    public RemoteResponse<Boolean> delBucket(@RequestBody BucketDelReq req) {
        
        BaseS3Client.delBucket(req.getBucket());
        return RemoteResponse.success();
    }
    
}
