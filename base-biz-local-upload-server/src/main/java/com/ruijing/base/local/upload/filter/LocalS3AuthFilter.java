package com.ruijing.base.local.upload.filter;

import com.ruijing.base.local.upload.constant.SysConstant;
import com.ruijing.base.local.upload.enums.ApiErrorEnum;
import com.ruijing.base.local.upload.filter.local.LocalFilterChain;
import com.ruijing.base.local.upload.filter.local.LocalHttpContext;
import com.ruijing.base.local.upload.filter.local.LocalHttpFilter;
import com.ruijing.base.local.upload.web.s3.server.response.ApiResponseUtil;
import com.ruijing.base.local.upload.web.s3.utils.S3AuthUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author WangJieLong
 * @date 6/1/2024
 * @description s3 auth
 */
public class LocalS3AuthFilter implements LocalHttpFilter {
    
    
    @Override
    public void doFilter(LocalHttpContext context, LocalFilterChain<LocalHttpContext, IOException, ServletException> chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = context.getRequest();
        HttpServletResponse servletResponse = context.getResponse();
        
        if (!servletRequest.getServletPath().startsWith("/s3")) {
            chain.doFilter(context);
            return;
        }
        
        boolean flag = true;
        String authorization = servletRequest.getHeader("Authorization");
//        String credential = servletRequest.getParameter("X-Amz-Credential");
        try {
            if (StringUtils.isNotBlank(authorization)) {
                flag = S3AuthUtil.validAuthorizationHead(servletRequest, SysConstant.accessKeyId, SysConstant.secretAccessKey);
            }
            if (!flag) {
                flag = S3AuthUtil.validAuthorizationUrl(servletRequest, SysConstant.accessKeyId, SysConstant.secretAccessKey);
            }
        } catch (Exception e) {
            flag = false;
        }
        if (!flag) {
            servletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            ApiResponseUtil.writeError(ApiErrorEnum.ErrInvalidRequest);
            return;
        }
        chain.doFilter(context);
    }
    
}
