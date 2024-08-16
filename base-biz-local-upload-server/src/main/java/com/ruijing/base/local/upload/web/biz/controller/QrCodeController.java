package com.ruijing.base.local.upload.web.biz.controller;

import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcMethod;
import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcMethodParam;
import com.ruijing.base.local.upload.web.biz.resp.QrCodeResp;
import com.ruijing.fundamental.api.remote.RemoteResponse;
import com.ruijing.fundamental.cat.annotation.CatAnnotation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 二维码接口
 * @Author: WangJieLong
 * @Date: 2023-12-19
 */
@CatAnnotation
@RestController
@RequestMapping("/qrCode")
public class QrCodeController {
    
    
    @RequestMapping("/front/mergeQrCode")
    @RpcMethod("二维码前端合并")
    public RemoteResponse<QrCodeResp> mergeQrCode(HttpServletRequest request, @RpcMethodParam("前端文件账号id") @RequestParam("id") String id,
                                                  @RpcMethodParam("logo文件") @RequestParam("file") MultipartFile logo,
                                                  @RpcMethodParam("logo宽度") @RequestParam(value = "width", defaultValue = "0", required = false) int width,
                                                  @RpcMethodParam("logo高度") @RequestParam(value = "height", defaultValue = "0", required = false) int height,
                                                  @RpcMethodParam("原始二维码文件链接") @RequestParam(value = "qrCodeOriginUrl") String qrCodeOriginUrl) {
        
        return RemoteResponse.<QrCodeResp>custom().setFailure("还没实现呢");
    }
    
}
