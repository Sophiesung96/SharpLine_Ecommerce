package com.example.demo01.src.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);

    public static void saveFile(String uploadDiv, String FileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDiv);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);

        }
        try {
            InputStream inputStream = multipartFile.getInputStream();
            //add path right after the uploadPath's directory absolute path
            Path filePath = uploadPath.resolve(FileName);
            //copy inputStream into filePath
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


