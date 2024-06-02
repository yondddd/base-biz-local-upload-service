package com.ruijing.base.local.upload.util;


import com.ruijing.base.local.upload.web.s3.context.S3Globals;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;


public class IpUtil {

    public static boolean IsLanIp(String ip) {
        if (ip == null) {
            return false;
        }
        return ip.startsWith("10.") || ip.startsWith("172.") || ip.startsWith("192.") || ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1");
    }

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Original-Forwarded-For");
        }
        return ip;
    }

    public static String getIpFromContext(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String ip = (String) request.getAttribute("MY_REQUEST_IP");
        if (ip == null) {
            ip = IpUtil.getIp(request);
            if (ip != null) {
                request.setAttribute("MY_REQUEST_IP", ip);
            }
        }
        return ip;
    }

    public static String getHostName(HttpServletRequest request) {
        if (S3Globals.globalIsDistErasure) {
            return S3Globals.globalLocalNodeName;
        }
        return request.getRemoteHost();
    }

}
