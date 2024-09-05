package com.ruijing.base.local.upload.constant;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-09-05
 */
public interface AmzHeaders {
    
    String AmzObjectLockEnabled = "x-amz-bucket-object-lock-enabled";
    
    String AmzRequestID = "x-amz-request-id";
    
    String AmzRequestHostID = "x-amz-id-2";
    
    String BaseIOForceCreate = "x-baseio-force-create";
    
    String VersionID = "versionId";
    
    String MetaContentDisposition = "x-amz-meta-content-disposition";
    
    String MetaContentType = "x-amz-meta-content-type";
    
}
