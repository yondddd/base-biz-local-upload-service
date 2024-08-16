package com.ruijing.base.local.upload.web.s3.server.service;

import com.ruijing.base.local.upload.web.s3.server.req.ObjectPutReq;

/**
 * @Description: object service
 * @Author: WangJieLong
 * @Date: 2024-07-25
 */
public interface ObjectService {
    
    String putObject(ObjectPutReq req);
    
}
