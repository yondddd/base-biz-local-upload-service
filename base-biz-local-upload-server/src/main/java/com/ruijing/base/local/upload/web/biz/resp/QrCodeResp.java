package com.ruijing.base.local.upload.web.biz.resp;

import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModel;
import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModelProperty;

import java.io.Serializable;

/**
 * @Description: 二维码
 * @Author: WangJieLong
 * @Date: 2023-12-19
 */
@RpcModel("二维码")
public class QrCodeResp implements Serializable {
    
    private static final long serialVersionUID = 2408032494668821453L;
    
    @RpcModelProperty("二维码链接")
    private String url;
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Override
    public String toString() {
        return "QrCodeResp{" +
                "url='" + url + '\'' +
                '}';
    }
}
