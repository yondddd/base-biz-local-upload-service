package com.ruijing.base.local.upload.web.business.req;

import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModel;
import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 前端回收参数
 * @Author: WangJieLong
 * @Date: 2023-07-11
 */
@RpcModel("前端回收参数")
public class FrontRecycleReq implements Serializable {
    
    private static final long serialVersionUID = -4162196625275621446L;
    
    @RpcModelProperty("前端账号id")
    private String id;
    @RpcModelProperty("oss链接")
    private List<String> urls;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public List<String> getUrls() {
        return urls;
    }
    
    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
    
    @Override
    public String toString() {
        return "FrontRecycleRequest{" +
                "id='" + id + '\'' +
                ", urls=" + urls +
                '}';
    }
}
