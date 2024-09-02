package com.ruijing.base.local.upload.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-09-02
 */
public class BucketDomain {
    
    // todo 做成可配置
    private static final Map<String, String> map = new HashMap<>();
    
    static {
        map.put("localhost", "data");
    }
    
    public static String getBucket(String domain) {
        return map.get(domain);
    }
    
}
