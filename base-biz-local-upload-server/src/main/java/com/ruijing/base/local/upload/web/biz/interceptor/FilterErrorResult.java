package com.ruijing.base.local.upload.web.biz.interceptor;

import com.ruijing.fundamental.cat.Cat;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: HGD
 * @Date: 2020/4/27 14:58
 * @Description:
 */
public class FilterErrorResult {
    
    public static void returnFailedJson(HttpServletResponse response, int code, String msg) {
        String tips = String.format("{\"code\": \"0001\",\"absolutePath\": null,\"bucket\": null,\"relativePath\": null,\"success\": false,\"msg\": \"%s\"}", msg);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(code);
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(tips);
            out.flush();
        } catch (IOException e) {
            Cat.logError("写出失败", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
