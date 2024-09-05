package com.ruijing.base.local.upload.filter;

import com.ruijing.base.local.upload.enums.ApiErrorEnum;
import com.ruijing.base.local.upload.filter.local.LocalHttpContext;
import com.ruijing.base.local.upload.filter.local.LocalHttpFilter;
import com.ruijing.base.local.upload.filter.local.LocalHttpFilterChain;
import com.ruijing.base.local.upload.web.s3.server.response.ApiResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@WebFilter(urlPatterns = "/*", asyncSupported = true)
public class ServletFilter implements Filter {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ServletFilter.class);
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LocalHttpFilterChain.getDefaultChain()
                // s3鉴权
                .addFilter(new LocalS3AuthFilter())
                // s3上下文
                .addFilter(new LocalS3ContextFilter());
        Filter.super.init(filterConfig);
    }
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            List<LocalHttpFilter> customFilters = request.getServletPath().startsWith("/ping")
                    ? Collections.emptyList()
                    : LocalHttpFilterChain.getDefaultFilters();
            
            LocalHttpFilterChain
                    .custom()
                    .setFilters(customFilters)
                    .setLastFilter((req, resp) -> filterChain.doFilter(request, response))
                    .doFilter(LocalHttpContext.custom().setRequest(request).setResponse(response));
        } catch (Exception e) {
            LOGGER.error("<|>servletPath:{}<|>", request.getServletPath(), e);
            ApiResponseUtil.writeError(ApiErrorEnum.ErrInvalidRequest);
        }
    }
    
}
