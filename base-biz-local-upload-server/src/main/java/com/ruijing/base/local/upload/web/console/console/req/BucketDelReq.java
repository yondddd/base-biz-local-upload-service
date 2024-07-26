package com.ruijing.base.local.upload.web.console.console.req;

import com.ruijing.fundamental.api.annotation.Model;
import com.ruijing.fundamental.api.annotation.ModelProperty;

import java.io.Serializable;

/**
 * @Description: bucket del
 * @Author: WangJieLong
 * @Date: 2024-07-26
 */
@Model("bucket del")
public class BucketDelReq implements Serializable {
    
    private static final long serialVersionUID = -4664218379052461451L;
    
    
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
        return "BucketDelReq{" +
                "bucketName='" + bucketName + '\'' +
                '}';
    }
}
