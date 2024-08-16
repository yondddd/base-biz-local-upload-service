package com.ruijing.base.local.upload.web.biz.resp;

import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModel;
import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModelProperty;

import java.io.Serializable;

/**
 * @Description: 分片合并结果
 * @Author: WangJieLong
 * @Date: 2023/5/18
 */
@RpcModel("分片合并结果")
public class CompleteMultipartUploadResp implements Serializable {
    
    private static final long serialVersionUID = 1986837733066004524L;
    
    @RpcModelProperty("文件链接")
    private String url;
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Override
    public String toString() {
        return "CompleteMultipartUploadResp{" +
                "url='" + url + '\'' +
                '}';
    }
}
