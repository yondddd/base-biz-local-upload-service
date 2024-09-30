package com.ruijing.base.local.upload.manager;

import com.ruijing.base.local.upload.constant.BucketConstant;
import com.ruijing.base.local.upload.constant.SysConstant;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Description: init
 * @Author: WangJieLong
 * @Date: 2024-05-28
 */
@Component
public class SystemInitManager implements CommandLineRunner {
    
    
    @Override
    public void run(String... args) throws Exception {
        // 初始化目录
        String dataPath = SysConstant.dataPath;
        String root = "/" + dataPath;
        Path path = Paths.get(root);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
        Path temPath = Paths.get(SysConstant.tempPath);
        if (!Files.exists(temPath)) {
            Files.createDirectory(temPath);
        }
        // 配置bucket
        Path baseBucket = Paths.get(root + "/" + BucketConstant.BaseMetaBucket);
        if (!Files.exists(baseBucket)) {
            Files.createDirectory(baseBucket);
        }
        // 分片上传记录
        Path multipartPath = Paths.get(SysConstant.MULTIPART_PATH);
        if (!Files.exists(multipartPath)) {
            Files.createDirectory(multipartPath);
        }
        // 初始化Access Key Id ,Access Key Secret
        
        //todo 生成默认console账号
    }
    
}
