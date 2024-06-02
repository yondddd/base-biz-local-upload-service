package com.ruijing.base.local.upload.filter.local;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LocalHttpContext {

    private HttpServletRequest request;

    private HttpServletResponse response;

    public static LocalHttpContext custom() {
        return new LocalHttpContext();
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public LocalHttpContext setRequest(HttpServletRequest request) {
        this.request = request;
        return this;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public LocalHttpContext setResponse(HttpServletResponse response) {
        this.response = response;
        return this;
    }
}