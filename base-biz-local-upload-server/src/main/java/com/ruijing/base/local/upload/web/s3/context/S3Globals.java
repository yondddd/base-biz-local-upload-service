package com.ruijing.base.local.upload.web.s3.context;

import com.ruijing.fundamental.api.annotation.Model;

/**
 * @Description: Global Util
 * @Author: WangJieLong
 * @Date: 2024-05-29
 */
@Model("Global Util")
public class S3Globals {

    /**
     * Indicates if the running minio server is distributed setup.
     */
    public static boolean globalIsDistErasure = false;

    /**
     * The name of this local node, fetched from arguments
     */
    public static String globalLocalNodeName;


}
