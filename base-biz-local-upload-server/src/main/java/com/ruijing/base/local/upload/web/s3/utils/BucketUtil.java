package com.ruijing.base.local.upload.web.s3.utils;

import com.ruijing.base.local.upload.constant.BucketConstant;
import com.ruijing.fundamental.api.annotation.Model;

/**
 * @Description: bucket util
 * @Author: WangJieLong
 * @Date: 2024-05-28
 */
@Model("bucket util")
public class BucketUtil {

    /**
     * @return 是否系统内部bucket(非业务)
     */
    public static boolean isBaseMetaBucketName(String bucketName) {
        return bucketName.startsWith(BucketConstant.BaseMetaBucket);
    }


}
