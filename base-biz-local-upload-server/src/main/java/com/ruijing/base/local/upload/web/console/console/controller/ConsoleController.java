package com.ruijing.base.local.upload.web.console.console.controller;

import com.ruijing.base.local.upload.web.console.console.req.BucketCreateReq;
import com.ruijing.base.local.upload.web.console.console.req.BucketDelReq;
import com.ruijing.base.local.upload.web.console.console.req.ObjectDelReq;
import com.ruijing.base.local.upload.web.console.console.req.ObjectsDelReq;
import com.ruijing.base.local.upload.web.console.console.resp.BucketVO;
import com.ruijing.base.local.upload.web.s3.client.BaseS3Client;
import com.ruijing.fundamental.api.remote.RemoteResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.Bucket;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/console")
public class ConsoleController {
    
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
    
    @PostMapping("/putObject")
    @ResponseBody
    public RemoteResponse<String> putObject(HttpServletRequest request,
                                            @RequestParam("bucket") String bucket,
                                            @RequestParam("file") MultipartFile file) throws IOException {
        
        String url = BaseS3Client.putObject(bucket, file.getOriginalFilename(), file.getInputStream(), file.getSize());
        return RemoteResponse.success(url);
    }
    
    @PostMapping("/deleteObjects")
    @ResponseBody
    public RemoteResponse<Boolean> deleteObjects(@RequestBody ObjectsDelReq req) {
        
        BaseS3Client.deleteObjects(req.getBucket(), req.getKeys());
        return RemoteResponse.success();
    }
    
    @PostMapping("/deleteObject")
    @ResponseBody
    public RemoteResponse<Boolean> deleteObject(@RequestBody ObjectDelReq req) {
        
        BaseS3Client.deleteObject(req.getBucket(), req.getKey());
        return RemoteResponse.success();
    }
    
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
    
}
