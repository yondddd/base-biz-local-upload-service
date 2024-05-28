package com.ruijing.base.local.upload.constant;

import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModel;

/**
 * @Description: s3 headers
 * @Author: WangJieLong
 * @Date: 2024-05-28
 */
@RpcModel("s3 headers")
public class S3Headers {
    
    // Object lock enabled
    public static final String AmzObjectLockEnabled = "x-amz-bucket-object-lock-enabled";
    
    
}
