package com.ruijing.base.local.upload.filter;

import com.ruijing.base.local.upload.filter.local.LocalFilterChain;
import com.ruijing.base.local.upload.filter.local.LocalHttpContext;
import com.ruijing.base.local.upload.filter.local.LocalHttpFilter;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @Description: console auth
 * @Author: WangJieLong
 * @Date: 2024-08-16
 */
public class LocalConsoleAuthFilter implements LocalHttpFilter {
    
    @Override
    public void doFilter(LocalHttpContext context, LocalFilterChain<LocalHttpContext, IOException, ServletException> chain) throws IOException, ServletException {
        // console need token, auth jwt
        chain.doFilter(context);
    }
    
}
