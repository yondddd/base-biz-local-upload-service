package com.ruijing.base.local.upload.filter;

import com.ruijing.base.local.upload.config.SystemConfig;
import com.ruijing.base.local.upload.filter.local.LocalHttpContext;
import com.ruijing.base.local.upload.filter.local.LocalHttpFilterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/*", asyncSupported = true)
public class ServletFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServletFilter.class);

    private final SystemConfig systemConfig;

    public ServletFilter(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LocalHttpFilterChain.getDefaultChain()
                // s3鉴权
                .addFilter(new LocalS3AuthFilter(systemConfig))
                // s3上下文
                .addFilter(new LocalS3ContextFilter());
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            String servletPath = request.getServletPath();
            if (servletPath.startsWith("/ping")) {
                filterChain.doFilter(servletRequest, servletResponse);
            }
            LocalHttpFilterChain
                    .custom()
                    .setFilters(LocalHttpFilterChain.getDefaultFilters())
                    .setLastFilter((req, resp) -> filterChain.doFilter(request, response))
                    .doFilter(LocalHttpContext.custom().setRequest(request).setResponse(response));
        } catch (Exception e) {
//            LOGGER.error();
            // Bad Gateway
//            ResponseUtil.writeErrorResponse();
        }
    }

}
