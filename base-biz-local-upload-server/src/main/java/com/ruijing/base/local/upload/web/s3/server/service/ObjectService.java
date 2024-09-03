package com.ruijing.base.local.upload.web.s3.server.service;

import com.ruijing.base.local.upload.web.s3.server.req.*;
import com.ruijing.base.local.upload.web.s3.server.resp.CompleteMultipartUploadResult;
import com.ruijing.base.local.upload.web.s3.server.resp.InitiateMultipartUploadResult;
import com.ruijing.base.local.upload.web.s3.server.resp.MultiUploadPartResult;

import java.nio.file.Path;

/**
 * @Description: object service
 * @Author: WangJieLong
 * @Date: 2024-07-25
 */
public interface ObjectService {
    
    String putObject(ObjectPutReq req);
    
    Path getObject(ObjectGetReq req);
    
    void delObject(ObjectDelReq req);
    
    InitiateMultipartUploadResult createMultipartUpload(MultipartUploadInitReq req);
    
    MultiUploadPartResult uploadPart(MultiUploadPartReq req);
    
    CompleteMultipartUploadResult completeMultipartUpload(MultipartUploadCompleteReq req);
    
}
