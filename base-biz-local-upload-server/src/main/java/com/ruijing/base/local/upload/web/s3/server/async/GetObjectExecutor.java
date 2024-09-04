package com.ruijing.base.local.upload.web.s3.server.async;

import com.ruijing.base.local.upload.config.BucketDomain;
import com.ruijing.base.local.upload.enums.ApiErrorEnum;
import com.ruijing.base.local.upload.web.s3.server.req.ObjectGetReq;
import com.ruijing.base.local.upload.web.s3.server.response.ApiResponseUtil;
import com.ruijing.base.local.upload.web.s3.server.service.ObjectService;
import com.ruijing.fundamental.common.threadpool.NamedThreadFactory;
import com.ruijing.fundamental.concurrent.ListenableThreadPoolExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-09-04
 */
@Component
public class GetObjectExecutor {
    
    @Resource
    private ObjectService objectService;
    
    private GetObjectExecutor() {
        EXECUTOR_SERVICE.execute(this::poll);
    }
    
    private static final ExecutorService EXECUTOR_SERVICE =
            new ListenableThreadPoolExecutor(50, 50, new NamedThreadFactory("GetObjectExecutor-"));
    
    private static final BlockingQueue<AsyncContext> queue = new LinkedBlockingQueue<>(1000);
    
    public static boolean offer(AsyncContext context) {
        return queue.offer(context);
    }
    
    private void poll() {
        while (true) {
            AsyncContext context = null;
            try {
                context = queue.poll(10, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                //
            }
            if (context != null) {
                doExecute(context);
            }
        }
    }
    
    private void doExecute(AsyncContext context) {
        EXECUTOR_SERVICE.execute(() -> {
            HttpServletRequest httpServerRequest = (HttpServletRequest) context.getRequest();
            HttpServletResponse httpServerResponse = (HttpServletResponse) context.getResponse();
            try {
                
                String serverName = httpServerRequest.getServerName();
                String bucket = BucketDomain.getBucket(serverName);
                if (bucket == null) {
                    ApiResponseUtil.writeError(httpServerRequest, httpServerResponse, ApiErrorEnum.ErrNoSuchKey);
                    return;
                }
                String fullPath = (String) httpServerRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
                ObjectGetReq objectGetReq = ObjectGetReq.custom().setBucket(bucket).setKey(fullPath);
                Path path = objectService.getObject(objectGetReq);
                if (!Files.exists(path)) {
                    ApiResponseUtil.writeError(httpServerRequest, httpServerResponse, ApiErrorEnum.ErrNoSuchKey);
                    return;
                }
                try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ)) {
                    httpServerResponse.setContentType(Files.probeContentType(path));
                    long fileSize = Files.size(path);
                    fileChannel.transferTo(0, fileSize, Channels.newChannel(httpServerResponse.getOutputStream()));
                }
            } catch (Exception e) {
                ApiResponseUtil.writeError(httpServerRequest, httpServerResponse, ApiErrorEnum.ErrBusy);
            } finally {
                context.complete();
            }
        });
    }
    
}
