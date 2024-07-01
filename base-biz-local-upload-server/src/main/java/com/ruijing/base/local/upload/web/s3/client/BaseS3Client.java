package com.ruijing.base.local.upload.web.s3.client;

import com.ruijing.base.local.upload.config.SystemConfig;
import com.ruijing.base.local.upload.util.CommonUtil;
import com.ruijing.fundamental.api.annotation.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;

import java.net.URI;

/**
 * @Description: s3 client
 * @Author: WangJieLong
 * @Date: 2024-07-01
 */
@Model("s3 client")
@Component
public class BaseS3Client {
    
    @Autowired
    private SystemConfig systemConfig;
    
    private static S3Client S3_CLIENT;
    
    {
        StaticCredentialsProvider provider = StaticCredentialsProvider.create(AwsBasicCredentials.create(systemConfig.getAccessKeyId(), systemConfig.getSecretAccessKey()));
        S3_CLIENT = S3Client.builder()
                .credentialsProvider(provider)
                .endpointOverride(URI.create(CommonUtil.getApiPath() + "s3/"))
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).chunkedEncodingEnabled(false).build())
                .region(Region.US_EAST_1)
                .build();
    }
    
    public static void createBucket(String bucketName) {
        CreateBucketRequest request = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();
        S3_CLIENT.createBucket(request);
    }
    
}
