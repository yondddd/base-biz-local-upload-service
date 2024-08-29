package com.ruijing.base.local.upload.web.console.console.req;

import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModelProperty;

import java.io.Serializable;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-08-29
 */
public class CreateMultipartUploadReq implements Serializable {
    
    private static final long serialVersionUID = -2011027204864124471L;
    
    @RpcModelProperty(value = "文件名称(带文件后缀)", required = true)
    private String fileName;
    @RpcModelProperty(value = "文件md5(前端生成),同一文件生成的md5需相同", required = true)
    private String fileMd5;
    @RpcModelProperty(value = "文件总大小(字节数)", required = true)
    private Long fileSize;
    @RpcModelProperty(value = "文件分片数量", required = true)
    private Integer totalPart;
    
    
}
