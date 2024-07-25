package com.ruijing.base.local.upload.web.s3.server.service;

import com.ruijing.base.local.upload.web.s3.server.req.PutObjectReq;

/**
 * @Description: object service
 * @Author: WangJieLong
 * @Date: 2024-07-25
 */
public interface ObjectService {
    
    String putObject(PutObjectReq req);
    
}
