package com.example.demo01.src.Pojo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import software.amazon.awssdk.services.s3.S3Client;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class AmazonS3UtilTest {

    @Autowired
    private Environment environment;

    @Autowired
    private S3Client s3Client;

    @Autowired
    private AmazonS3Util s3Util; // Injected AmazonS3Util instance

    @Test
    void testEnvVariableExists() {
        String awsKey = environment.getProperty("AWS_ACCESS_KEY_ID");
        assertNotNull(awsKey, "AWS_ACCESS_KEY_ID should not be null");
    }

    @Test
    void testListFolder() {
        String folderName = "product-images/18";
        List<String> files = s3Util.listFolder(folderName);

        assertNotNull(files);
        System.out.println("Files in folder: " + files);
    }

    @Test
    void testUploadFile() throws FileNotFoundException {
        String fileName = "news.jpeg";
        String filePath = "/Users/sophie/Desktop/images/" + fileName;
        InputStream inputStream = new FileInputStream(filePath);
        String folderName = "test-upload";

        s3Util.uploadFile(folderName, fileName, inputStream);

        // Verify file exists in S3
        List<String> files = s3Util.listFolder(folderName);
        assertTrue(files.contains(folderName + "/" + fileName), "Uploaded file should exist");
    }
}
