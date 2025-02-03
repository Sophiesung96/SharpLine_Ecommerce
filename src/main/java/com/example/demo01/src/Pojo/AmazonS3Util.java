package com.example.demo01.src.Pojo;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AmazonS3Util {

    private final String bucketName;
    private final S3Client s3Client;

    // Constructor: Inject bucket name & S3Client
    public AmazonS3Util(String bucketName, S3Client s3Client) {
        this.bucketName = bucketName;
        this.s3Client = s3Client;
        System.setProperty(SdkSystemSetting.AWS_REGION.property(), "ap-southeast-2");
    }

    public List<String> listFolder(String folderName) {
        try {
            ListObjectsRequest request = ListObjectsRequest.builder()
                    .bucket(bucketName)
                    .prefix(folderName)
                    .build();
            ListObjectsResponse response = s3Client.listObjects(request);

            List<String> fileList = response.contents()
                    .stream()
                    .map(S3Object::key)
                    .collect(Collectors.toList());

            log.info("Files in folder {}: {}", folderName, fileList);
            return fileList;
        } catch (SdkClientException e) {
            log.error("Error listing folder {}: {}", folderName, e.getMessage());
            return List.of();
        }
    }

    public void uploadFile(String folderName, String fileName, InputStream inputStream) {
        String objectKey = folderName + "/" + fileName;
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .acl("public-read")
                .build();

        try (inputStream) {
            int contentLength = inputStream.available();
            s3Client.putObject(request, RequestBody.fromInputStream(inputStream, contentLength));
            log.info("Successfully uploaded file: {}", objectKey);
        } catch (IOException ex) {
            log.error("Could not upload file {} to Amazon S3", objectKey, ex);
        }
    }

    public void deleteFile(String fileName) {
        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();
            s3Client.deleteObject(request);
            log.info("Deleted file: {}", fileName);
        } catch (SdkClientException e) {
            log.error("Error deleting file {}: {}", fileName, e.getMessage());
        }
    }

    public void removeFolder(String folderName) {
        try {
            ListObjectsRequest request = ListObjectsRequest.builder()
                    .bucket(bucketName)
                    .prefix(folderName)
                    .build();
            ListObjectsResponse response = s3Client.listObjects(request);

            response.contents().forEach(s3Object -> {
                DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(s3Object.key())
                        .build();
                s3Client.deleteObject(deleteRequest);
                log.info("Deleted: {}", s3Object.key());
            });

            log.info("Successfully removed folder: {}", folderName);
        } catch (SdkClientException e) {
            log.error("Error removing folder {}: {}", folderName, e.getMessage());
        }
    }
}
