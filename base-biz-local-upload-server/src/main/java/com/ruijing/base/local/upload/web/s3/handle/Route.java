package com.ruijing.base.local.upload.web.s3.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.function.BiConsumer;

public class Route {

    // Example of how this handler might be used internally
    private BiConsumer<HttpServletResponse, HttpServletRequest> handler;

    public Route handler(BiConsumer<HttpServletResponse, HttpServletRequest> handler) {
        // Assuming there's some internal mechanism to store the handler
        // and this method returns the Route object for method chaining.
        this.handler = handler;
        return this;
    }

    public Route handlerFunc(BiConsumer<HttpServletResponse, HttpServletRequest> f) {
        return this.handler(f);
    }


    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        if (handler != null) {
            handler.accept(response, request);
        } else {
            // Handle the case where no handler is set
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
