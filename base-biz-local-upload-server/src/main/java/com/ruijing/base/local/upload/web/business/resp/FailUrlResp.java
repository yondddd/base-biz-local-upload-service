package com.ruijing.base.local.upload.web.business.resp;

import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModel;
import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModelProperty;

import java.io.Serializable;

/**
 * @Description: 失败链接
 * @Author: WangJieLong
 * @Date: 2023-07-11
 */
@RpcModel("失败链接")
public class FailUrlResp implements Serializable {
    
    private static final long serialVersionUID = -7456012278698954338L;
    
    public FailUrlResp(String url, String msg) {
        this.url = url;
        this.msg = msg;
    }
    
    @RpcModelProperty("文件链接")
    private String url;
    @RpcModelProperty("错误信息")
    private String msg;
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    @Override
    public String toString() {
        return "FailUrlResp{" +
                "url='" + url + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
