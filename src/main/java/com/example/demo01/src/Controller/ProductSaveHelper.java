package com.example.demo01.src.Controller;

import com.example.demo01.src.Pojo.AmazonS3Util;
import com.example.demo01.src.Pojo.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
public class ProductSaveHelper {

    @Autowired
    AmazonS3Util amazonS3Util;

    public void deleteExtraImagesWereRemovedOnForm(Product product){

        String extraImageDir="/product-images/"+product.getId()+"/extras";
        List<String> listObjectKeys= amazonS3Util.listFolder(extraImageDir);
        for(String objectKey:listObjectKeys){
            int lastIndexOfSlash=objectKey.lastIndexOf("/");
            String fileName=objectKey.substring(lastIndexOfSlash+1,objectKey.length());
            //Deleting the extra image from the S3 if the product has no such file
            if(!product.getExtraImage().equals(fileName)){
                amazonS3Util.deleteFile(objectKey);
                log.info("Deleted extra image: "+objectKey);
            }
        }
    }

}
