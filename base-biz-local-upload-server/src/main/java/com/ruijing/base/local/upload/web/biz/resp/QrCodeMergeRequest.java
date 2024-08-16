package com.ruijing.base.local.upload.web.biz.resp;

import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModel;
import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModelProperty;

import java.io.Serializable;

/**
 * @Description: 二维码请求
 * @Author: WangJieLong
 * @Date: 2023-12-19
 */
@RpcModel("二维码请求")
public class QrCodeMergeRequest implements Serializable {
    
    private static final long serialVersionUID = 5661227360921855751L;
    
    @RpcModelProperty("前端文件账号id")
    private String id;
    @RpcModelProperty("二维码原始url")
    private String qrCodeOriginUrl;
    @RpcModelProperty("二维码logoUrl")
    private String qrCodeLogoUrl;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getQrCodeOriginUrl() {
        return qrCodeOriginUrl;
    }
    
    public void setQrCodeOriginUrl(String qrCodeOriginUrl) {
        this.qrCodeOriginUrl = qrCodeOriginUrl;
    }
    
    public String getQrCodeLogoUrl() {
        return qrCodeLogoUrl;
    }
    
    public void setQrCodeLogoUrl(String qrCodeLogoUrl) {
        this.qrCodeLogoUrl = qrCodeLogoUrl;
    }
    
    @Override
    public String toString() {
        return "QrCodeMergeRequest{" +
                "id='" + id + '\'' +
                ", qrCodeOriginUrl='" + qrCodeOriginUrl + '\'' +
                ", qrCodeLogoUrl='" + qrCodeLogoUrl + '\'' +
                '}';
    }
}
