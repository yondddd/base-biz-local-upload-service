package com.ruijing.base.local.upload.constant;

import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModel;

/**
 * @Description: s3 headers
 * @Author: WangJieLong
 * @Date: 2024-05-28
 */
@RpcModel("s3 headers")
public class S3Headers {

    /**
     * Object lock enabled
     */
    public static final String AmzObjectLockEnabled = "x-amz-bucket-object-lock-enabled";

    /**
     * Response request id.
     */
    public static final String AmzRequestID = "x-amz-request-id";

    public static final String AmzRequestHostID = "x-amz-id-2";

    // Create special flag to force create a bucket
    public static final String BaseIOForceCreate = "x-baseio-force-create";
    
    public static final String VersionID = "versionId";
}
