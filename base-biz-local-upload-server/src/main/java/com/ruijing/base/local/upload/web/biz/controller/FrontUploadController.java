package com.ruijing.base.local.upload.web.biz.controller;

import com.ruijing.base.local.upload.constant.BucketConstant;
import com.ruijing.base.local.upload.web.biz.enums.FileTypeEnum;
import com.ruijing.base.local.upload.web.biz.req.FrontRecycleReq;
import com.ruijing.base.local.upload.web.biz.resp.FileUploadResp;
import com.ruijing.base.local.upload.web.biz.resp.RecycleResp;
import com.ruijing.base.local.upload.web.s3.client.BaseS3Client;
import com.ruijing.fundamental.api.annotation.MethodParam;
import com.ruijing.fundamental.api.remote.RemoteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description: front upload
 * @Author: WangJieLong
 * @Date: 2024-07-26
 */
@RestController
public class FrontUploadController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FrontUploadController.class);
    
    @RequestMapping("/front")
    public @ResponseBody
    FileUploadResp uploadFileByType(ServletInputStream request, @RequestParam("id") @MethodParam(value = "文件账号id", required = true) String id,
                                    @RequestParam("file") @MethodParam(value = "文件", required = true) MultipartFile file,
                                    @RequestParam("type") @MethodParam(value = "文件类型", required = true) FileTypeEnum type,
                                    @RequestParam(value = "width", defaultValue = "0", required = false) @MethodParam(value = "图片宽度") int width,
                                    @RequestParam(value = "height", defaultValue = "0", required = false) @MethodParam(value = "图片高度") int height,
                                    @RequestParam(value = "pathName", defaultValue = "", required = false) @MethodParam(value = "指定路径（用于链接需带上标识）") String pathName,
                                    @RequestParam(value = "location", defaultValue = "", required = false) @MethodParam(value = "水印定位信息") String location,
                                    @RequestParam(value = "userName", defaultValue = "", required = false) @MethodParam(value = "水印用户信息") String userName,
                                    @RequestParam(value = "recycleTime", defaultValue = "0", required = false) @MethodParam(value = "文件回收时间") long recycleTime,
                                    @RequestParam(value = "convertFormat", defaultValue = "", required = false) @MethodParam(value = "svg 图片转换格式") String format,
                                    @RequestParam(value = "dpi", defaultValue = "0", required = false) @MethodParam(value = "svg 图片转换dpi") int dpi) {
        
        
        try {
            String url = BaseS3Client.putObject(BucketConstant.DEFAULT_BUCKET, file.getOriginalFilename(), file.getInputStream(), file.getSize());
            return FileUploadResp.success(url);
        } catch (Exception e) {
            LOGGER.error("<|>FrontUploadController_front<|>", e);
            return FileUploadResp.failed("没实现呢");
        }
    }
    
    @RequestMapping("/front/recycle")
    public @ResponseBody
    RemoteResponse<RecycleResp> recycle(HttpServletRequest request, @RequestBody FrontRecycleReq param) {
        // todo 暂时没必要删。第二期
        return RemoteResponse.<RecycleResp>custom().setFailure("没实现呢");
    }
    
}
