package com.ruijing.base.local.upload.web.biz.controller;

import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcMethodParam;
import com.ruijing.base.local.upload.web.biz.req.InitMultipartUploadRequest;
import com.ruijing.base.local.upload.web.biz.req.UploadIdRequest;
import com.ruijing.base.local.upload.web.biz.resp.CompleteMultipartUploadResp;
import com.ruijing.base.local.upload.web.biz.resp.InitMultipartUploadResp;
import com.ruijing.base.local.upload.web.biz.resp.UploadPartResp;
import com.ruijing.base.local.upload.web.biz.util.LinkAssertUtil;
import com.ruijing.base.swagger.api.rpc.annotation.RpcApi;
import com.ruijing.base.swagger.api.rpc.annotation.RpcMethod;
import com.ruijing.fundamental.api.remote.RemoteResponse;
import com.ruijing.pearl.annotation.PearlValue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 分片上传接口
 * @Author: WangJieLong
 * @Date: 2023-12-01
 */
@RequestMapping("/partUpload")
@RestController
@RpcApi(value = "文件分片上传接口")
public class PartUploadController {
    
    /**
     * 阿里云oss最小分片大小为100kb，但推荐是1m-10m，这里设置最小10m
     */
    @PearlValue(key = "minimum.part.file.size", defaultValue = "10485760")
    private static Integer MINIMUM_PART_FILE_SIZE;
    
    /**
     * 阿里云oss最大分片个数为100000，但最好不要太多
     */
    @PearlValue(key = "max.part.size", defaultValue = "1000")
    private static Integer MAX_PART_SIZE;
    
    @RpcMethod("初始化分片上传")
    @PostMapping("/initiateMultipartUpload")
    public RemoteResponse<InitMultipartUploadResp> initiateMultipartUpload(@RequestBody InitMultipartUploadRequest request, HttpServletRequest servletRequest) {
        String errorMsg = LinkAssertUtil.linkValid()
                .notBlank(request.getFileName(), "文件名称为空")
                .notBlank(request.getFileMd5(), "文件md5为空")
                .notNull(request.getFileSize(), "文件总大小为空")
                .notNull(request.getTotalPart(), "文件分片数为空")
                .getErrorMsg();
        if (StringUtils.isNotBlank(errorMsg)) {
            return RemoteResponse.<InitMultipartUploadResp>custom().setFailure(errorMsg);
        }
        return RemoteResponse.<InitMultipartUploadResp>custom().setFailure("没实现呢");
    }
    
    
    @RpcMethod("上传文件分片(请按顺序上传)")
    @PostMapping("/uploadPart")
    public RemoteResponse<UploadPartResp> uploadPart(HttpServletRequest servletRequest,
                                                     @RpcMethodParam(value = "上传账号id", required = false) @RequestParam("id") String id,
                                                     @RpcMethodParam(value = "上传id", required = true) @RequestParam("uploadId") String uploadId,
                                                     @RpcMethodParam(value = "分片文件", required = true) @RequestParam("partFile") MultipartFile partFile,
                                                     @RpcMethodParam(value = "文件分片序号", required = true) @RequestParam("partNum") Integer partNum) {
        String errorMsg = LinkAssertUtil.linkValid()
                .notNull(uploadId, "上传id为空")
                .notNull(partFile, "上传文件为空")
                .notNull(partNum, "分片id为空").getErrorMsg();
        if (StringUtils.isNotBlank(errorMsg)) {
            return RemoteResponse.<UploadPartResp>custom().failure(errorMsg);
        }
        return RemoteResponse.<UploadPartResp>custom().setFailure("没实现呢");
    }
    
    
    @RpcMethod("分片合并")
    @PostMapping("/completeMultipartUpload")
    public RemoteResponse<CompleteMultipartUploadResp> completeMultipartUpload(@RequestBody UploadIdRequest request, HttpServletRequest servletRequest) {
        String errorMsg = LinkAssertUtil.linkValid()
                .notBlank(request.getUploadId(), "上传id为空").getErrorMsg();
        if (StringUtils.isNotBlank(errorMsg)) {
            return RemoteResponse.<CompleteMultipartUploadResp>custom().failure(errorMsg);
        }
        return RemoteResponse.<CompleteMultipartUploadResp>custom().setFailure("没实现呢");
    }
    
    
    @RpcMethod("取消分片上传")
    @PostMapping("/abortMultipartUpload")
    public RemoteResponse<Boolean> abortMultipartUpload(@RequestBody UploadIdRequest request, HttpServletRequest servletRequest) {
        String errorMsg = LinkAssertUtil.linkValid()
                .notBlank(request.getUploadId(), "上传id为空").getErrorMsg();
        if (StringUtils.isNotBlank(errorMsg)) {
            return RemoteResponse.<Boolean>custom().failure(errorMsg);
        }
        return RemoteResponse.<Boolean>custom().setFailure("没实现呢");
    }
    
}
