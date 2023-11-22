package com.example.demo01.src.Pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductImage {

    private int id;
    private String name;
    private int productId;
    private int index;

    public String getImagePath(){
        if(this.id<0|| this.name==null){
            return "/image/default.jpeg";
        }
        else{


            return"/product-images/"+this.productId+"/extras/"+this.name;
        }
    }

}
