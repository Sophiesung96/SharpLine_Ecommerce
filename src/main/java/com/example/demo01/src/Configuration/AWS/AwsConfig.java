package com.example.demo01.src.Configuration.AWS;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsConfig {

    @Value("${aws.accessKeyId}")
    private String accessKey;
    @Value("${aws.secretAccessKey}")
    private String secretKey;
    @Value("${aws.region}")
    private String region;
    @Bean
    public S3Client s3Client() {
        System.setProperty(SdkSystemSetting.AWS_ACCESS_KEY_ID.property(), accessKey);
        System.setProperty(SdkSystemSetting.AWS_SECRET_ACCESS_KEY.property(), secretKey);
        System.setProperty(SdkSystemSetting.AWS_REGION.property(), region);
        return S3Client.builder()
                .region(Region.of(region)) // Set your region
                .build();
    }
}
