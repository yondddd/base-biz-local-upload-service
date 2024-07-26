package com.ruijing.base.local.upload.web.business.controller;

import com.ruijing.base.local.upload.web.business.enums.FileTypeEnum;
import com.ruijing.base.local.upload.web.business.resp.FileUploadResp;
import com.ruijing.pearl.annotation.PearlValue;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class BackUploadController {
    
    private final Logger logger = LoggerFactory.getLogger(BackUploadController.class);
    
    @PearlValue(key = "upload.file.max.size", defaultValue = "104857600")
    private static Integer MAX_DATA_LENGTH;
    @Value("${ossClient.download.prefix}")
    private String downloadPrefix;
    
    
    @RequestMapping("/upload")
    public @ResponseBody
    FileUploadResp uploadPictureWithPath(@RequestParam("file") MultipartFile file,
                                         @RequestParam("type") FileTypeEnum type,
                                         @RequestParam(value = "width", defaultValue = "0", required = false) int width,
                                         @RequestParam(value = "height", defaultValue = "0", required = false) int height,
                                         @RequestParam(value = "coverUrl", defaultValue = "", required = false) String coverUrl,
                                         @RequestParam(value = "recycleTime", defaultValue = "0", required = false) long recycleTime,
                                         HttpServletRequest request) {
        return FileUploadResp.failed("还没实现呢");
    }
    
    @RequestMapping("/delete")
    public @ResponseBody
    FileUploadResp delete(@RequestBody List<String> filePaths, HttpServletRequest request) {
        if (CollectionUtils.isEmpty(filePaths)) {
            return FileUploadResp.failed("删除文件路径不能为空");
        }
        return FileUploadResp.failed("还没实现呢");
    }
    
}
