package com.ruijing.base.local.upload.web.console.console.controller;

import com.ruijing.base.local.upload.util.PathUtils;
import com.ruijing.base.local.upload.util.UUIDUtil;
import com.ruijing.base.local.upload.web.console.console.req.BucketCreateReq;
import com.ruijing.base.local.upload.web.console.console.req.BucketDelReq;
import com.ruijing.base.local.upload.web.console.console.req.ObjectDelReq;
import com.ruijing.base.local.upload.web.console.console.req.ObjectsDelReq;
import com.ruijing.base.local.upload.web.console.console.resp.BucketVO;
import com.ruijing.base.local.upload.web.s3.client.BaseS3Client;
import com.ruijing.fundamental.api.remote.RemoteResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/console")
public class ConsoleController {
    
    @PostMapping("/putBucket")
    @ResponseBody
    public RemoteResponse<Boolean> putBucket(@RequestBody BucketCreateReq req) {
        CreateBucketRequest request = CreateBucketRequest.builder()
                .bucket(req.getBucketName())
                .build();
        BaseS3Client.putBucket(request);
        return RemoteResponse.success();
    }
    
    @PostMapping("/delBucket")
    @ResponseBody
    public RemoteResponse<Boolean> delBucket(@RequestBody BucketDelReq req) {
        DeleteBucketRequest request = DeleteBucketRequest.builder()
                .bucket(req.getBucketName())
                .build();
        BaseS3Client.delBucket(request);
        return RemoteResponse.success();
    }
    
    @PostMapping("/putObject")
    @ResponseBody
    public RemoteResponse<String> putObject(HttpServletRequest request,
                                            @RequestParam("bucketName") String bucketName,
                                            @RequestParam("file") MultipartFile file) throws IOException {
        
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String objectName = UUIDUtil.generateId() + "." + extension;
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectName)
                .build();
        software.amazon.awssdk.core.sync.RequestBody requestBody = software.amazon.awssdk.core.sync.RequestBody.fromInputStream(file.getInputStream(), file.getSize());
        BaseS3Client.putObject(putObjectRequest, requestBody);
        return RemoteResponse.success(PathUtils.buildPath(bucketName, objectName));
    }
    
    
    @PostMapping("/deleteObjects")
    @ResponseBody
    public RemoteResponse<Boolean> deleteObjects(@RequestBody ObjectsDelReq req) {
        
        List<ObjectIdentifier> collect = req.getKeys().stream()
                .map(x -> ObjectIdentifier.builder().key(x).build()).collect(Collectors.toList());
        DeleteObjectsRequest deleteObjectsRequest = DeleteObjectsRequest.builder()
                .bucket(req.getBucket())
                .delete(Delete.builder().objects(collect).build()).build();
        BaseS3Client.delObjects(deleteObjectsRequest);
        return RemoteResponse.success();
    }
    
    @PostMapping("/deleteObject")
    @ResponseBody
    public RemoteResponse<Boolean> deleteObject(@RequestBody ObjectDelReq req) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(req.getBucket())
                .key(req.getKey()).build();
        BaseS3Client.delObject(deleteObjectRequest);
        return RemoteResponse.success();
    }
    
    
    @PostMapping("/listBucket")
    @ResponseBody
    public RemoteResponse<List<BucketVO>> listBucket() {
        ListBucketsResponse listBucketsResponse = BaseS3Client.listBuckets();
        List<software.amazon.awssdk.services.s3.model.Bucket> buckets = listBucketsResponse.buckets();
        List<BucketVO> data = new ArrayList<>();
        for (Bucket bucket : buckets) {
            BucketVO item = new BucketVO();
            item.setName(bucket.name());
            item.setCreationDate(Date.from(bucket.creationDate()));
            data.add(item);
        }
        return RemoteResponse.success(data);
    }
    
    // listObjects、partUpload
    
    
}
