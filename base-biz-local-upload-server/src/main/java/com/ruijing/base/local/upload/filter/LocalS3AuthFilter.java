package com.ruijing.base.local.upload.filter;

import com.ruijing.base.local.upload.config.SystemConfig;
import com.ruijing.base.local.upload.enums.ApiErrorEnum;
import com.ruijing.base.local.upload.filter.local.LocalFilterChain;
import com.ruijing.base.local.upload.filter.local.LocalHttpContext;
import com.ruijing.base.local.upload.filter.local.LocalHttpFilter;
import com.ruijing.base.local.upload.web.s3.response.ApiResponseUtil;
import com.ruijing.base.local.upload.web.s3.utils.S3AuthUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yond
 * @date 6/1/2024
 * @description s3 auth
 */
public class LocalS3AuthFilter implements LocalHttpFilter {
    
    private final SystemConfig systemConfig;
    
    public LocalS3AuthFilter(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }
    
    @Override
    public void doFilter(LocalHttpContext context, LocalFilterChain<LocalHttpContext, IOException, ServletException> chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = context.getRequest();
        HttpServletResponse servletResponse = context.getResponse();
        
        boolean flag = false;
        String authorization = servletRequest.getHeader("Authorization");
        if (StringUtils.isNotBlank(authorization)) {
            try {
                flag = S3AuthUtil.validAuthorizationHead(servletRequest, systemConfig.getAccessKeyId(), systemConfig.getSecretAccessKey());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            authorization = servletRequest.getParameter("X-Amz-Credential");
            if (StringUtils.isNotBlank(authorization)) {
                try {
                    flag = S3AuthUtil.validAuthorizationUrl(servletRequest, systemConfig.getAccessKeyId(), systemConfig.getSecretAccessKey());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (!flag) {
            servletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            ApiResponseUtil.writeError(ApiErrorEnum.ErrInvalidRequest);
        }
        chain.doFilter(context);
    }
    
}
