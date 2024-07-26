package com.ruijing.base.local.upload.web.business.resp;

import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModel;
import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModelProperty;

import java.io.Serializable;

/**
 * @Description: 上传文件分片结果
 * @Author: WangJieLong
 * @Date: 2023/5/18
 */
@RpcModel("上传文件分片结果")
public class UploadPartResp implements Serializable {
    
    private static final long serialVersionUID = -3760632996446958873L;
    
    @RpcModelProperty("分片文件唯一标识")
    private String eTag;
    
    public String getETag() {
        return eTag;
    }
    
    public void setETag(String eTag) {
        this.eTag = eTag;
    }
    
    @Override
    public String toString() {
        return "UploadChunkResp{" +
                "eTag='" + eTag + '\'' +
                '}';
    }
}
