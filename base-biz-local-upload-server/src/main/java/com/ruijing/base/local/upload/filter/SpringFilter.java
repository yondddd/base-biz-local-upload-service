package com.ruijing.base.local.upload.filter;

import com.ruijing.base.local.upload.web.s3.response.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Order(10)
@Component
@WebFilter(urlPatterns = "/*", asyncSupported = true)
public class SpringFilter implements Filter {
    
    private final List<LocalFilter> filterList;
    
    public SpringFilter(List<LocalFilter> filterList) {
        this.filterList = filterList;
    }
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringFilter.class);
    
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getServletPath();
        try {
            if (path.startsWith("/ping")) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                LocalFilterChain chain = new LocalSimpleFilterChain(filterList);
                chain.doGatewayFilter(request, response);
            }
        } catch (Exception e) {
//            LOGGER.error();
            // Bad Gateway
//            ResponseUtil.writeErrorResponse();
        }
    }
    
}
