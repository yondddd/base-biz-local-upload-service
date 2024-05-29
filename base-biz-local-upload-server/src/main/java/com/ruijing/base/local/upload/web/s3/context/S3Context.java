package com.ruijing.base.local.upload.web.s3.context;


/**
 * @author yond
 * @date 5/29/2024
 * @description s3 context
 */
public class S3Context {

    private static final ThreadLocal<S3Context> S3_CONTEXT_LOCAL = new ThreadLocal<>();

    // 纠删码相关的
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

    public static S3Context getS3Context() {
        return S3_CONTEXT_LOCAL.get();
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public S3Context setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
        return this;
    }

    public String getRequestId() {
        return requestId;
    }

    public S3Context setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public S3Context setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
        return this;
    }

    public String getHost() {
        return host;
    }

    public S3Context setHost(String host) {
        this.host = host;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public S3Context setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public String getBucketName() {
        return bucketName;
    }

    public S3Context setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    public String getObjectName() {
        return objectName;
    }

    public S3Context setObjectName(String objectName) {
        this.objectName = objectName;
        return this;
    }

    public String getVersionId() {
        return versionId;
    }

    public S3Context setVersionId(String versionId) {
        this.versionId = versionId;
        return this;
    }

}
