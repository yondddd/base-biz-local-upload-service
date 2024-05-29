package com.ruijing.base.local.upload.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@WebFilter(urlPatterns = "/*", asyncSupported = true)
public class MySpringFilter implements Filter {

    private final List<LocalFilter> filterList;

    public MySpringFilter(List<LocalFilter> filterList) {
        this.filterList = filterList;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(MySpringFilter.class);


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
