package com.ruijing.base.local.upload.web.admin.console.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruijing.base.local.upload.model.Bucket;
import com.ruijing.base.local.upload.model.Result;
import com.ruijing.base.local.upload.util.ConvertOp;
import com.ruijing.base.local.upload.util.S3ClientUtil;
import com.ruijing.base.local.upload.web.admin.console.req.BucketCreateReq;
import com.ruijing.base.local.upload.web.s3.client.BaseS3Client;
import com.ruijing.fundamental.api.remote.RemoteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/console")
@ConditionalOnProperty(name = "system.console", havingValue = "true", matchIfMissing = false)
public class AdminController {

    @Autowired
    private S3ClientUtil s3ClientUtil;

    @PostMapping("/putBucket")
    @ResponseBody
    public RemoteResponse<Boolean> putBucket(@RequestBody BucketCreateReq req) {
        CreateBucketRequest request = CreateBucketRequest.builder()
                .bucket(req.getBucketName())
                .build();
        BaseS3Client.putBucket(request);
        return RemoteResponse.success();
    }


    @PostMapping("/listBucket")
    @ResponseBody
    public Result listBucket() {
        List<software.amazon.awssdk.services.s3.model.Bucket> bucketList = s3ClientUtil.getBucketList();
        List<Bucket> bucketInfoList = new ArrayList<>();
        for (software.amazon.awssdk.services.s3.model.Bucket item : bucketList) {
            Bucket bucket = new Bucket();
            bucket.setName(item.name());
            bucket.setCreationDate(item.creationDate().toString());
            bucketInfoList.add(bucket);
        }
        return Result.okResult().add("obj", bucketInfoList);
    }

    @PostMapping("/headBucket")
    @ResponseBody
    public Result headBucket(@RequestBody Map<String, Object> params) {
        String bucketName = ConvertOp.convert2String(params.get("bucketName"));
        boolean checkExist = s3ClientUtil.headBucket(bucketName);
        return Result.okResult().add("obj", checkExist);
    }

    @PostMapping("/deleteBucket")
    @ResponseBody
    public Result deleteBucket(@RequestBody Map<String, Object> params) {
        String bucketName = ConvertOp.convert2String(params.get("bucketName"));
        s3ClientUtil.deleteBucket(bucketName);
        return Result.okResult();
    }

    @PostMapping("/listObjects")
    @ResponseBody
    public Result listObjects(@RequestBody Map<String, Object> params) {
        String bucketName = ConvertOp.convert2String(params.get("bucketName"));
        String prefix = ConvertOp.convert2String(params.get("prefix"));
        JSONArray objectInfoList = new JSONArray();
        List<S3Object> s3ObjectList = s3ClientUtil.getObjectList(bucketName, prefix);
        for (S3Object s3Object : s3ObjectList) {
            JSONObject objectInfo = new JSONObject();
            objectInfo.put("key", s3Object.key());
            if (s3Object.lastModified() != null) {
                objectInfo.put("size", s3Object.size());
                objectInfo.put("lastModified", s3Object.lastModified());
            }
            objectInfoList.add(objectInfo);
        }
        return Result.okResult().add("obj", objectInfoList);
    }

    @PostMapping("/headObject")
    @ResponseBody
    public Result headObject(@RequestBody Map<String, Object> params) {
        String bucketName = ConvertOp.convert2String(params.get("bucketName"));
        String key = ConvertOp.convert2String(params.get("key"));
        HashMap headInfo = s3ClientUtil.headObject(bucketName, key);
        if (headInfo.containsKey("noExist")) {
            return Result.okResult().add("obj", false);
        } else {
            return Result.okResult().add("obj", true).add("head", headInfo);
        }
    }

    @PostMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        String bucketName = request.getParameter("bucketName");
        String key = request.getParameter("key");
        s3ClientUtil.upload(bucketName, key, file.getInputStream());
        return Result.okResult();
    }

    @PostMapping("/createMultipartUpload")
    @ResponseBody
    public Result createMultipartUpload(@RequestBody Map<String, Object> params) throws Exception {
        String bucketName = ConvertOp.convert2String(params.get("bucketName"));
        String key = ConvertOp.convert2String(params.get("key"));
        String uploadID = s3ClientUtil.createMultipartUpload(bucketName, key);
        return Result.okResult().add("obj", uploadID);
    }

    @PostMapping("/uploadPart")
    @ResponseBody
    public Result uploadPart(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        String bucketName = request.getParameter("bucketName");
        String key = request.getParameter("key");
        String uploadID = request.getParameter("uploadID");
        int partNumber = ConvertOp.convert2Int(request.getParameter("partNumber"));
        String etag = s3ClientUtil.uploadPart(bucketName, key, uploadID, partNumber, file.getInputStream());
        return Result.okResult().add("obj", etag);
    }

    @PostMapping("/completeMultipartUpload")
    @ResponseBody
    public Result completeMultipartUpload(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
        String bucketName = ConvertOp.convert2String(params.get("bucketName"));
        String key = ConvertOp.convert2String(params.get("key"));
        String uploadID = ConvertOp.convert2String(params.get("uploadID"));
        List<Map<String, Object>> partArray = (List<Map<String, Object>>) params.get("partList");
        List<CompletedPart> partList = new ArrayList<>();
        for (int i = 0; i < partArray.size(); i++) {
            Map<String, Object> item = partArray.get(i);
            int partNumber = ConvertOp.convert2Int(item.get("partNumber"));
            String eTag = ConvertOp.convert2String(item.get("eTag"));
            CompletedPart completedPart = CompletedPart.builder().partNumber(partNumber).eTag(eTag).build();
            partList.add(completedPart);
        }
        String fileEtag = s3ClientUtil.completeMultipartUpload(bucketName, key, uploadID, partList);
        return Result.okResult().add("obj", fileEtag);
    }

    @PostMapping("/getFileBytes")
    @ResponseBody
    public Result getFileBytes(@RequestBody Map<String, Object> params) throws Exception {
        String bucketName = ConvertOp.convert2String(params.get("bucketName"));
        String key = ConvertOp.convert2String(params.get("key"));
        byte[] data = s3ClientUtil.getFileByte(bucketName, key);
        return Result.okResult().add("obj", data);
    }

    @PostMapping("/download")
    @ResponseBody
    public Result download(@RequestBody Map<String, Object> params) throws Exception {
        String bucketName = ConvertOp.convert2String(params.get("bucketName"));
        String key = ConvertOp.convert2String(params.get("key"));
        String url = s3ClientUtil.getDownLoadUrl(bucketName, key);
        return Result.okResult().add("obj", url);
    }

    @PostMapping("/delete")
    @ResponseBody
    public Result delete(@RequestBody Map<String, Object> params) throws Exception {
        String bucketName = ConvertOp.convert2String(params.get("bucketName"));
        String key = ConvertOp.convert2String(params.get("key"));
        s3ClientUtil.delete(bucketName, key);
        return Result.okResult();
    }

    @PostMapping("/copy")
    @ResponseBody
    public Result copy(@RequestBody Map<String, Object> params) throws Exception {
        String sourceBucketName = ConvertOp.convert2String(params.get("sourceBucketName"));
        String sourceKey = ConvertOp.convert2String(params.get("sourceKey"));
        String targetBucketName = ConvertOp.convert2String(params.get("targetBucketName"));
        String targetKey = ConvertOp.convert2String(params.get("targetKey"));
        s3ClientUtil.copyObject(sourceBucketName, sourceKey, targetBucketName, targetKey);
        return Result.okResult();
    }

}
