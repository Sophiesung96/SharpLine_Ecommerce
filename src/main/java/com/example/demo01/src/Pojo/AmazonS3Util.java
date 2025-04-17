package com.example.demo01.src.Pojo;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
@Slf4j
public class AmazonS3Util {

    private static final String BUCKET_NAME;

    static {
        BUCKET_NAME = System.getenv("AWS_BUCKET_NAME");
        // Setting up AWS region
        System.setProperty(SdkSystemSetting.AWS_REGION.property(), "ap-southeast-2");
    }

    public static List<String> listFolder(String folderName) {
        List<String>list=new ArrayList<>();
        try (S3Client s3Client = S3Client.builder().build()) {
            ListObjectsRequest listObjectsRequest = ListObjectsRequest.builder()
                    .bucket(BUCKET_NAME)
                    .prefix(folderName)
                    .build();
            ListObjectsResponse listObjectsResponse = s3Client.listObjects(listObjectsRequest);
            List<S3Object> contents = listObjectsResponse.contents();
            ListIterator<S3Object> listIterator = contents.listIterator();
            while (listIterator.hasNext()) {
                S3Object s3Object = listIterator.next();
                list.add(s3Object.key());
                System.out.println("Key: " + s3Object.key());
                System.out.println("Owner: " + s3Object.owner());
            };

        } catch (SdkClientException e) {
            // Handle S3 client exceptions
            System.err.println("Error occurred: " + e.getMessage());
        }
        return list;
    }



    public static void uploadFile(String folderName, String fileName, InputStream inputStream){
        S3Client s3Client = S3Client.builder().build();
        PutObjectRequest putObjectRequest=PutObjectRequest
                .builder()
                .bucket(BUCKET_NAME)
                .key(folderName+"/"+fileName)
                .acl("public-read")
                .build();
        try(inputStream){
            int contentLength= inputStream.available();
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream,contentLength));
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error("Could not upload file to Amazon s3",ex);
        }
    }

    public static void deleteFile(String fileName){
        S3Client s3Client = S3Client.builder().build();
        DeleteObjectRequest deleteObjectRequest=DeleteObjectRequest
                .builder()
                .bucket(BUCKET_NAME)
                .key(fileName)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }

    public static void removeFolder(String folderName){
        try (S3Client s3Client = S3Client.builder().build()) {
            ListObjectsRequest listObjectsRequest = ListObjectsRequest.builder()
                    .bucket(BUCKET_NAME)
                    .prefix(folderName)
                    .build();
            ListObjectsResponse listObjectsResponse = s3Client.listObjects(listObjectsRequest);
            List<S3Object> contents = listObjectsResponse.contents();
            ListIterator<S3Object> listIterator = contents.listIterator();

            while (listIterator.hasNext()) {
                S3Object s3Object = listIterator.next();
                DeleteObjectRequest deleteObjectRequest=DeleteObjectRequest
                        .builder()
                        .bucket(BUCKET_NAME)
                        .key(s3Object.key())
                        .build();
                s3Client.deleteObject(deleteObjectRequest);
                System.out.println("Deleted the folder:"+s3Object.key());
            }
        } catch (SdkClientException e) {
            // Handle S3 client exceptions
            System.err.println("Error occurred: " + e.getMessage());
        }
    }
}
