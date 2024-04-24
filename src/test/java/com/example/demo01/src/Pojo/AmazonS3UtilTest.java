package com.example.demo01.src.Pojo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AmazonS3UtilTest {


    @Test
    public void testListFolder() {

        // Call the method being tested
        String folderName = "product-images/18";
        AmazonS3Util.listFolder(folderName);
    }
    @Test
    public void testUploadFile() throws FileNotFoundException {
        String fileName="news.jpeg";
        String filePath="/Users/sophie/Desktop/images/"+fileName;
        InputStream inputStream=new FileInputStream(filePath);
        String folderName="test-upload";
        AmazonS3Util.uploadFile(folderName,fileName,inputStream);

    }

    @Test
    public void deleteUploadFile(){
        String fileName="test-upload/news.jpeg";
        AmazonS3Util.deleteFile(fileName);
    }

    @Test
    public void testRemoveFolder(){
        String folderName="test-upload";
        AmazonS3Util.removeFolder(folderName);
    }

}