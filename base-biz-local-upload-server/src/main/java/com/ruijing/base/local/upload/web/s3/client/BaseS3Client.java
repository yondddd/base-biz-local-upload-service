package com.ruijing.base.local.upload.web.s3.client;

import com.ruijing.base.local.upload.util.CommonUtil;
import com.ruijing.base.local.upload.util.PathUtils;
import com.ruijing.base.local.upload.util.UUIDUtil;
import org.apache.commons.io.FilenameUtils;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
        // todo 校验
        CreateBucketRequest request = CreateBucketRequest.builder()
                .bucket(bucket)
                .build();
        S3_CLIENT.createBucket(request);
    }
    
    public static void delBucket(String bucket) {
        DeleteBucketRequest request = DeleteBucketRequest.builder()
                .bucket(bucket)
                .build();
        S3_CLIENT.deleteBucket(request);
    }
    
    public static String putObject(String bucket,
                                   String fileName,
                                   InputStream inputStream,
                                   Long fileSize) {
        
        String extension = FilenameUtils.getExtension(fileName);
        String objectName = PathUtils.concatFileName(UUIDUtil.generateId(), extension);
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(objectName)
                .build();
        RequestBody requestBody = RequestBody.fromInputStream(inputStream, fileSize);
        S3_CLIENT.putObject(putObjectRequest, requestBody);
        return PathUtils.buildPath(bucket, objectName);
    }
    
    public static void deleteObjects(String bucket, List<String> keys) {
        List<ObjectIdentifier> collect = keys.stream()
                .map(x -> ObjectIdentifier.builder().key(x).build()).collect(Collectors.toList());
        DeleteObjectsRequest deleteObjectsRequest = DeleteObjectsRequest.builder()
                .bucket(bucket)
                .delete(Delete.builder().objects(collect).build()).build();
        S3_CLIENT.deleteObjects(deleteObjectsRequest);
    }
    
    public static void deleteObject(String bucket, String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key).build();
        S3_CLIENT.deleteObject(deleteObjectRequest);
    }
    
    public static List<Bucket> listBuckets() {
        ListBucketsResponse listBucketsResponse = S3_CLIENT.listBuckets();
        return listBucketsResponse.buckets();
    }
    
    public static String createMultipartUpload(String bucket, String fileName) {
        
        String extension = FilenameUtils.getExtension(fileName);
        String key = PathUtils.concatFileName(UUIDUtil.generateId(), extension);
        
        CreateMultipartUploadRequest request = CreateMultipartUploadRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
        CreateMultipartUploadResponse multipartUpload = S3_CLIENT.createMultipartUpload(request);
        return multipartUpload.uploadId();
    }
    
    public static String uploadPart(String uploadId,
                                    String bucket,
                                    String key,
                                    Integer partNum,
                                    InputStream inputStream,
                                    Long fileSize) {
        UploadPartRequest request = UploadPartRequest.builder()
                .uploadId(uploadId)
                .bucket(bucket)
                .key(key)
                .partNumber(partNum)
                .build();
        RequestBody requestBody = RequestBody.fromInputStream(inputStream, fileSize);
        UploadPartResponse response = S3_CLIENT.uploadPart(request, requestBody);
        return response.eTag();
    }
    
    public static String completeMultipartUpload(String uploadId,
                                                 String bucket,
                                                 String key,
                                                 List<CompletedPart> parts) {
        CompletedMultipartUpload multipart = CompletedMultipartUpload.builder()
                .parts(parts).build();
        CompleteMultipartUploadRequest request = CompleteMultipartUploadRequest.builder()
                .uploadId(uploadId)
                .bucket(bucket)
                .key(key)
                .multipartUpload(multipart)
                .build();
        CompleteMultipartUploadResponse response = S3_CLIENT.completeMultipartUpload(request);
        return "url";
    }
    
    public static void abortMultipartUpload(String uploadId, String bucket, String key) {
        AbortMultipartUploadRequest request = AbortMultipartUploadRequest.builder()
                .uploadId(uploadId)
                .bucket(bucket)
                .key(key).build();
        S3_CLIENT.abortMultipartUpload(request);
    }
    
}
