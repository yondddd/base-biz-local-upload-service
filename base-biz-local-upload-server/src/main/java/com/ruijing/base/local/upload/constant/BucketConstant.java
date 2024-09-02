package com.ruijing.base.local.upload.constant;

import com.ruijing.fundamental.api.annotation.Model;

/**
 * @Description: bucket constant
 * @Author: WangJieLong
 * @Date: 2024-05-28
 */
@Model("bucket constant")
public class BucketConstant {
    
    /**
     * 配置文件初始bucket
     */
    public static final String BaseMetaBucket = ".baseio.sys";
    /**
     * 默认bucket
     */
    public static final String DEFAULT_BUCKET = "data";
    
    public static final String TEM_PATH = "/.baseio.sys/tem";
    
    public static final String MULTIPART_PATH = "/.baseio.sys/multipart";
    
    public static final String CONFIG_PATH = "/.baseio.sys/config";
}
