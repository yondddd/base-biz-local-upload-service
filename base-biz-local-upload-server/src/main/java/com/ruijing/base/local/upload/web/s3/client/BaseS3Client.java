package com.ruijing.base.local.upload.web.s3.client;

import com.ruijing.base.local.upload.util.CommonUtil;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

/**
 * @Description: s3 client
 * @Author: WangJieLong
 * @Date: 2024-07-01
 */
public class BaseS3Client {
    
    private static final BaseS3Client singleton = new BaseS3Client();
    
    private static S3Client S3_CLIENT;
    
    public BaseS3Client() {
        StaticCredentialsProvider provider = StaticCredentialsProvider.create(AwsBasicCredentials.create("admin", "abcd@1234"));
        S3_CLIENT = S3Client.builder()
                .credentialsProvider(provider)
                .endpointOverride(URI.create(CommonUtil.getApiPath() + "s3/"))
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).chunkedEncodingEnabled(false).build())
                .region(Region.US_EAST_1)
                .build();
    }
    
    public static S3Client getInstance() {
        return S3_CLIENT;
    }
    
    public static void createBucket(String bucket) {
    
    }
    
}
