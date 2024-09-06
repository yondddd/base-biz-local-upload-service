package com.ruijing.base.local.upload.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class CommonUtil {
    
    public static String getApiPath() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String requestURL = request.getRequestURL().toString();
        String scheme = request.getScheme();
        String servaerName = request.getServerName();
        int port = request.getServerPort();
        String rootPageURL = scheme + ":" + "//" + servaerName + ":" + port + "/";
        return rootPageURL;
    }
}
