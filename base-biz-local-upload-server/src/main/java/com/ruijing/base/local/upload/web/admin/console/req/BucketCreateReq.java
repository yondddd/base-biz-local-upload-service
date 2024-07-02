package com.ruijing.base.local.upload.web.admin.console.req;

import com.ruijing.fundamental.api.annotation.Model;
import com.ruijing.fundamental.api.annotation.ModelProperty;

import java.io.Serializable;

/**
 * @Description: bucket create
 * @Author: WangJieLong
 * @Date: 2024-07-01
 */
@Model("bucket create")
public class BucketCreateReq implements Serializable {

    private static final long serialVersionUID = -7340146014021874696L;

    @ModelProperty("bucket name")
    private String bucketName;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    @Override
    public String toString() {
        return "BucketCreateReq{" +
                "bucketName='" + bucketName + '\'' +
                '}';
    }
}
