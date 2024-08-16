package com.ruijing.base.local.upload.web.biz.req;

import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModel;
import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModelProperty;

import java.io.Serializable;

/**
 * @Description: 上传id
 * @Author: WangJieLong
 * @Date: 2023/5/18
 */
@RpcModel("上传id")
public class UploadIdRequest implements Serializable {
    
    private static final long serialVersionUID = 7219643833795277203L;
    
    @RpcModelProperty("文件账号id")
    private String id;
    @RpcModelProperty("上传id")
    private String uploadId;
    @RpcModelProperty("过期时间，到达指定时间后删除此文件")
    private Long recycleTime;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getUploadId() {
        return uploadId;
    }
    
    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }
    
    public Long getRecycleTime() {
        return recycleTime;
    }
    
    public void setRecycleTime(Long recycleTime) {
        this.recycleTime = recycleTime;
    }
    
    @Override
    public String toString() {
        return "UploadIdRequest{" +
                "id='" + id + '\'' +
                ", uploadId='" + uploadId + '\'' +
                ", recycleTime=" + recycleTime +
                '}';
    }
}
