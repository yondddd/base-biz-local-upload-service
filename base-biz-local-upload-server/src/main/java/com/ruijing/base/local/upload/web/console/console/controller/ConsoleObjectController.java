package com.ruijing.base.local.upload.web.console.console.controller;

import com.ruijing.base.local.upload.web.console.console.req.ObjectDelReq;
import com.ruijing.base.local.upload.web.console.console.req.ObjectsDelReq;
import com.ruijing.base.local.upload.web.s3.client.BaseS3Client;
import com.ruijing.fundamental.api.remote.RemoteResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-09-23
 */
@RestController
@RequestMapping("/console/object")
public class ConsoleObjectController {
    
    @PostMapping("/putObject")
    @ResponseBody
    public RemoteResponse<String> putObject(HttpServletRequest request,
                                            @RequestParam("bucket") String bucket,
                                            @RequestParam("file") MultipartFile file) throws IOException {
        
        String url = BaseS3Client.putObject(bucket, file.getOriginalFilename(), file.getInputStream(), file.getSize());
        return RemoteResponse.success(url);
    }
    
    @PostMapping("/deleteObjects")
    @ResponseBody
    public RemoteResponse<Boolean> deleteObjects(@RequestBody ObjectsDelReq req) {
        
        BaseS3Client.deleteObjects(req.getBucket(), req.getKeys());
        return RemoteResponse.success();
    }
    
    @PostMapping("/deleteObject")
    @ResponseBody
    public RemoteResponse<Boolean> deleteObject(@RequestBody ObjectDelReq req) {
        
        BaseS3Client.deleteObject(req.getBucket(), req.getKey());
        return RemoteResponse.success();
    }
    
}
