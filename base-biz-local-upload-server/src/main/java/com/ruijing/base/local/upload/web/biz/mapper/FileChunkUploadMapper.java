package com.ruijing.base.local.upload.web.biz.mapper;

import com.ruijing.base.local.upload.constant.SysConstant;
import com.ruijing.base.local.upload.web.biz.dto.FileChunkUploadDO;
import com.ruijing.fundamental.common.util.JsonUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-09-06
 */
public class FileChunkUploadMapper {
    
    public synchronized static void write(FileChunkUploadDO record) {
        String key = SysConstant.MULTIPART_PATH + "/" + record.getOssUploadId();
        try {
            Files.write(Paths.get(key), JsonUtils.toJson(record).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static FileChunkUploadDO getByUploadId(String uploadId) {
        String key = SysConstant.MULTIPART_PATH + "/" + uploadId;
        Path path = Paths.get(key);
        String jsonString = null;
        try {
            jsonString = new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return JsonUtils.fromJson(jsonString, FileChunkUploadDO.class);
    }
    
}
