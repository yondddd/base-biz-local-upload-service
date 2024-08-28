package com.ruijing.base.local.upload.util;

import java.util.UUID;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-08-28
 */
public class UUIDUtil {
    
    public static String generateId() {
        return UUID.randomUUID().toString();
    }
    
}
