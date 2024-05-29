package com.ruijing.base.local.upload.web.s3.service;

import com.ruijing.fundamental.api.annotation.Model;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description: Global Util
 * @Author: WangJieLong
 * @Date: 2024-05-29
 */
@Model("Global Util")
public class GlobalUtil {
    
    private static final AtomicReference<String> globalDeploymentID = new AtomicReference<>("initialValue");
    
    public static void main(String[] args) {
        // Retrieve the current value of the atomic reference
        String deploymentID = globalDeploymentID.get();
        
        // Example: printing the deployment ID
        System.out.println("Deployment ID: " + deploymentID);
        String s = globalDeploymentID.get();
        System.out.println("Deployment ID: " + s);
    }
    
}
