package com.ruijing.base.local.upload.web.biz.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: HGD
 * @Date: 2020/3/30 15:32
 * @Description: sdk文件上传鉴权拦截器
 */
@Component
public class SdkUploadAuthInterceptor implements HandlerInterceptor {
    
    private final Logger logger = LoggerFactory.getLogger(SdkUploadAuthInterceptor.class);
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String business = request.getHeader("auth-accessKeyId") == null ? request.getHeader("auth_accessKeyId") : request.getHeader("auth-accessKeyId");
        String token = request.getHeader("auth-secretAccessKey") == null ? request.getHeader("auth_secretAccessKey") : request.getHeader("auth-secretAccessKey");
        String time = request.getHeader("auth-time") == null ? request.getHeader("auth_time") : request.getHeader("auth-time");
        logger.info("<|>appKey:{}<|>accessKeyId:{}<|><|>", request.getHeader("appKey"), business);
        if (StringUtils.isBlank(business) || StringUtils.isBlank(token) || StringUtils.isBlank(time)) {
            logger.info("<|>账号:{} 没有权限<|>", business);
            FilterErrorResult.returnFailedJson(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "没有权限");
            return false;
        }
        // todo 暂时先校验请求头
        return true;
    }
    
}
