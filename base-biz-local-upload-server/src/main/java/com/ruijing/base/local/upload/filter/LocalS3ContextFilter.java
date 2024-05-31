package com.ruijing.base.local.upload.filter;

import com.ruijing.base.local.upload.constant.S3Headers;
import com.ruijing.base.local.upload.web.s3.context.S3Context;
import com.ruijing.fundamental.api.annotation.Model;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Description: s3 context
 * @Author: WangJieLong
 * @Date: 2024-05-29
 */
@Model("s3 context")
@Order(20)
@Component
public class LocalS3ContextFilter implements LocalFilter {
    
    @Override
    public void doFilter(HttpServletRequest servletRequest, HttpServletResponse servletResponse, LocalFilterChain filterChain) throws IOException, ServletException {
        
        // 如果是s3开头才加？
        S3Context s3Context = S3Context.getCreateS3Context();
        String requestId = servletRequest.getHeader(S3Headers.AmzRequestID);
        Object pathVariables = servletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (pathVariables instanceof Map) {
            for (Map.Entry<?, ?> entry : ((Map<?, ?>) pathVariables).entrySet()) {
            
            }
//            String chatbotId = (String)pathVariables.get("classId");
        }
        
        
        filterChain.doFilter(servletRequest, servletResponse);
    }
    
}
