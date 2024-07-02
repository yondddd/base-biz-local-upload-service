package com.ruijing.base.local.upload.web.s3.server.options;


import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModelProperty;
import com.ruijing.base.local.upload.constant.S3Headers;
import com.ruijing.base.local.upload.enums.ApiErrorEnum;
import com.ruijing.base.local.upload.web.s3.server.response.ApiResponseUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class PutBucketOptions {

    private boolean lockEnabled;
    private boolean versioningEnabled;
    @RpcModelProperty("Create buckets even if they are already created.")
    private boolean forceCreate;
    @RpcModelProperty("only for site replication")
    private LocalDateTime createdAt;
    @RpcModelProperty("does not lock the make bucket call if set to 'true'")
    private boolean noLock;

    public boolean isLockEnabled() {
        return lockEnabled;
    }

    public void setLockEnabled(boolean lockEnabled) {
        this.lockEnabled = lockEnabled;
    }

    public boolean isVersioningEnabled() {
        return versioningEnabled;
    }

    public void setVersioningEnabled(boolean versioningEnabled) {
        this.versioningEnabled = versioningEnabled;
    }

    public boolean isForceCreate() {
        return forceCreate;
    }

    public void setForceCreate(boolean forceCreate) {
        this.forceCreate = forceCreate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isNoLock() {
        return noLock;
    }

    public void setNoLock(boolean noLock) {
        this.noLock = noLock;
    }


    public static PutBucketOptions extractOptions(HttpServletRequest httpServerRequest) {
        PutBucketOptions options = new PutBucketOptions();
        String objectLockEnabled = httpServerRequest.getHeader(S3Headers.AmzObjectLockEnabled);
        if (StringUtils.isNotBlank(objectLockEnabled)) {
            String lowerCase = objectLockEnabled.toLowerCase();
            switch (lowerCase) {
                case "true":
                case "false":
                    options.setLockEnabled(BooleanUtils.toBoolean(lowerCase));
                default:
                    ApiResponseUtil.writeError(ApiErrorEnum.ErrInvalidRequest);
            }
        }
        String baseIoForceCreate = httpServerRequest.getHeader(S3Headers.BaseIOForceCreate);
        if (StringUtils.isNotBlank(baseIoForceCreate)) {
            String lowerCase = baseIoForceCreate.toLowerCase();
            switch (lowerCase) {
                case "true":
                case "false":
                    options.setForceCreate(BooleanUtils.toBoolean(lowerCase));
                default:
                    ApiResponseUtil.writeError(ApiErrorEnum.ErrInvalidRequest);
            }
        }

        return options;
    }

}
