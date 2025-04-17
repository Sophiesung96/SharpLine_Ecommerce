package com.example.demo01.src.Pojo;

public class Constants {

    public String S3_BASE_URI;

    public String getS3BaseUri() {
        String bucketName = System.getenv("AWS_BUCKET_NAME");
        String region = System.getenv("AWS_REGION");
        if (bucketName != null && region != null) {
            String pattern = "https://%s.s3.%s.amazonaws.com";
            S3_BASE_URI = String.format(pattern, bucketName, region);
        } else {
            S3_BASE_URI = ""; // or some default URI
        }
        return S3_BASE_URI;
    }

}
