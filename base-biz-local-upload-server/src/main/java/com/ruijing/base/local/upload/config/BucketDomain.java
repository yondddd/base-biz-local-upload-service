package com.ruijing.base.local.upload.config;

import com.ruijing.fundamental.common.util.NetUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-09-02
 */
public class BucketDomain {
    
    private static final Map<String, String> map1 = new HashMap<>();
    private static final Map<String, String> map2 = new HashMap<>();
    
    static {
        // todo ip可配置
        String ipV4 = NetUtil.getIpV4();
        map1.put("localhost", "data");
        map1.put(ipV4, "data");
        map2.put("data", ipV4 + ":8888");
    }
    
    public static String getBucket(String domain) {
        return map1.get(domain);
    }
    
    public static String getDomain(String bucket) {
        return map2.get(bucket);
    }
    
}
