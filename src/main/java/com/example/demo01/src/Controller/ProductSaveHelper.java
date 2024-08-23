package com.example.demo01.src.Controller;

import com.example.demo01.src.Pojo.AmazonS3Util;
import com.example.demo01.src.Pojo.Product;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ProductSaveHelper {

    static void deleteExtraImagesWereRemovedOnForm(Product product){

        String extraImageDir="/product-images/"+product.getId()+"/extras";
        List<String> listObjectKeys= AmazonS3Util.listFolder(extraImageDir);
        for(String objectKey:listObjectKeys){
            int lastIndexOfSlash=objectKey.lastIndexOf("/");
            String fileName=objectKey.substring(lastIndexOfSlash+1,objectKey.length());
            //Deleting the extra image from the S3 if the product has no such file
            if(!product.getExtraImage().equals(fileName)){
                AmazonS3Util.deleteFile(objectKey);
                log.info("Deleted extra image: "+objectKey);
            }
        }
    }

}
