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
    private static final Map<String, String> map1 = new HashMap<>();
    private static final Map<String, String> map2 = new HashMap<>();
    
    static {
        map1.put("localhost", "data");
        map2.put("data", "localhost:8888");
    }
    
    public static String getBucket(String domain) {
        return map1.get(domain);
    }
    
    public static String getDomain(String bucket) {
        return map2.get(bucket);
    }
    
}
