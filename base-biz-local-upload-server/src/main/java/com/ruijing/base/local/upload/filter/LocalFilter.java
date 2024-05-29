package com.ruijing.base.local.upload.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LocalFilter {
    
    void doFilter(HttpServletRequest servletRequest, HttpServletResponse servletResponse, LocalFilterChain filterChain) throws Exception;

}
