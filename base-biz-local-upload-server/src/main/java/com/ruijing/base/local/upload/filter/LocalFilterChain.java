package com.ruijing.base.local.upload.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public interface LocalFilterChain extends FilterChain {
    
    void doGatewayFilter(HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    default void doFilter(ServletRequest var1, ServletResponse var2) {
        try {
            this.doGatewayFilter((HttpServletRequest) var1,  (HttpServletResponse) var2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
