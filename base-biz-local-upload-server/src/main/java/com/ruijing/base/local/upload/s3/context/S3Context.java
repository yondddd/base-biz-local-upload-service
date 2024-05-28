package com.ruijing.base.local.upload.s3.context;

import com.ruijing.fundamental.common.threadpool.local.InternalThreadLocal;

/**
 * @author yond
 * @date 5/29/2024
 * @description s3 context
 */
public class S3Context {

    private static final InternalThreadLocal<S3Context> S3_CONTEXT_LOCAL = new InternalThreadLocal<>();

    private String deploymentId;
    private String requestId;
    private String remoteHost;
    private String host;
    private String userAgent;
    private String bucketName;
    private String objectName;
    private String versionId;

    // 在filter里赋值
    public static S3Context getCreateS3Context() {
        S3Context s3Context = new S3Context();
        S3_CONTEXT_LOCAL.set(s3Context);
        return s3Context;
    }

}
