package com.example.demo01.src.Pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Integer id;
    private String name;
    private String alias;
    private String shortDescription;
    private String fullContent;

    private Date createdTime;
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date UpdatedTime;
    private float cost;
    private float price;
    private float discountPercent;
    private float length;
    private float width;
    private float height;
    private float weight;
    private String Mainimage;
    private String ExtraImage;
    private float averageRating;
    private Integer review;
    private Integer enabled;
    private Integer inStock;
    private Integer categoryId;
    private Integer brandId;
    private String categoryName;
    private String brandName;
    private List<ProductDetail> detail;

    public Product(Integer id) {
        this.id = id;
    }

    public String getMainImagePath(){
        if(this.id<0|| this.Mainimage==null){
            return "/image/default.jpeg";
        }
        else{

            return"/product-images/"+this.id+"/"+this.Mainimage;
        }
    }




    public float getDetailPrice(){
        if(discountPercent>0){
            float newprice=this.price*((100-discountPercent)/100);
            BigDecimal bd = new BigDecimal(newprice);
            BigDecimal roundOff = bd.setScale(2, RoundingMode.HALF_UP);
          return  roundOff.floatValue();
        }
        return this.discountPercent;
    }


}
