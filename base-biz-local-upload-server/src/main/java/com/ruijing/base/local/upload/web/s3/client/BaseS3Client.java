package com.ruijing.base.local.upload.web.s3.client;

import com.ruijing.base.local.upload.config.SystemConfig;
import com.ruijing.base.local.upload.util.CommonUtil;
import com.ruijing.fundamental.api.annotation.Model;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;

import java.net.URI;

/**
 * @Description: s3 client
 * @Author: WangJieLong
 * @Date: 2024-07-01
 */
@Model("s3 client")
@Component
public class BaseS3Client {
    
    private final SystemConfig systemConfig;
    
    private static S3Client S3_CLIENT;
    
    public BaseS3Client(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }
    
    public CreateBucketResponse putBucket(CreateBucketRequest request) {
        init();
        return S3_CLIENT.createBucket(request);
    }
    
    
    public void init() {
        if (S3_CLIENT != null) {
            return;
        }
        StaticCredentialsProvider provider = StaticCredentialsProvider.create(AwsBasicCredentials.create(systemConfig.getAccessKeyId(), systemConfig.getSecretAccessKey()));
        S3_CLIENT = S3Client.builder()
                .credentialsProvider(provider)
                .endpointOverride(URI.create(CommonUtil.getApiPath() + "s3/"))
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).chunkedEncodingEnabled(false).build())
                .region(Region.US_EAST_1)
                .build();
    }
    
}
