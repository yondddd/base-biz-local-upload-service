package com.ruijing.base.local.upload.filter;

import com.ruijing.base.local.upload.constant.S3Constant;
import com.ruijing.base.local.upload.constant.S3Headers;
import com.ruijing.base.local.upload.filter.local.LocalFilterChain;
import com.ruijing.base.local.upload.filter.local.LocalHttpContext;
import com.ruijing.base.local.upload.filter.local.LocalHttpFilter;
import com.ruijing.base.local.upload.util.IpUtil;
import com.ruijing.base.local.upload.web.s3.server.context.S3Context;
import org.apache.http.HttpHeaders;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author yond
 * @date 6/1/2024
 * @description s3 context
 */
public class LocalS3ContextFilter implements LocalHttpFilter {

    @Override
    public void doFilter(LocalHttpContext context, LocalFilterChain<LocalHttpContext, IOException, ServletException> chain) throws IOException, ServletException {

        HttpServletRequest servletRequest = context.getRequest();

        if (!servletRequest.getServletPath().startsWith("/s3")) {
            chain.doFilter(context);
        }

        S3Context s3Context = S3Context.getCreateS3Context()
                .setRequestId(servletRequest.getHeader(S3Headers.AmzRequestID))
                .setRemoteHost(IpUtil.getIpFromContext(servletRequest))
                .setHost(IpUtil.getHostName(servletRequest))
                .setUserAgent(servletRequest.getHeader(HttpHeaders.USER_AGENT))
                .setVersionId(servletRequest.getHeader(S3Headers.VersionID));

        Object pathVariables = servletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (pathVariables instanceof Map) {
            s3Context.setBucketName((String) ((Map<?, ?>) pathVariables).get(S3Constant.BUCKET));
            s3Context.setObjectName((String) ((Map<?, ?>) pathVariables).get(S3Constant.OBJECT));
        }

        chain.doFilter(context);
    }
}
