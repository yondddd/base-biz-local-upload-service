package com.ruijing.base.local.upload.web.s3.options;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class PutBucketOptions {

    private boolean lockEnabled;
    private boolean versioningEnabled;
    private boolean forceCreate;
    private LocalDateTime createdAt;
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
        return null;
    }

}