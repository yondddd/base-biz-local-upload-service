package com.ruijing.base.local.upload.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(30)
@Component
public class LocalForwardFilter implements LocalFilter{

    @Override
    public void doFilter(HttpServletRequest servletRequest, HttpServletResponse servletResponse, LocalFilterChain filterChain) throws IOException, ServletException {
        servletRequest.getRequestDispatcher(servletRequest.getServletPath()).forward(servletRequest, servletResponse);
    }

}
