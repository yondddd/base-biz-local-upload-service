package com.ruijing.base.local.upload.filter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LocalSimpleFilterChain implements LocalFilterChain {
    
    private final List<LocalFilter> list;
    
    private int pos = 0;
    
    public LocalSimpleFilterChain(List<LocalFilter> list) {
        this.list = list;
    }
    
    @Override
    public void doGatewayFilter(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (pos < list.size()) {
            list.get(pos++).doFilter(request, response, this);
        }
    }
    
}
