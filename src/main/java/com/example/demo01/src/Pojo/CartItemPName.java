package com.example.demo01.src.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemPName {

    private int productId;
    private String productName;
    private String productImage;
    private String productAlias;
    private float price;
    private float discountPercent;
    private int quantity;
    private float width;
    private float height;
    private float length;
    private float weight;
    private float ShippingCost;

    public String getImageMainPath() {
        if (this.productId == 0 || this.productImage == null) {
            return "/image/default.jpeg";
        }
        else {


            return "https://sharplinebucket.s3.ap-southeast-2.amazonaws.com/product-images/"+this.productId+"/"+this.productImage;
        }
    }

    public float getDetailPrice() {
        if (discountPercent > 0) {
            float newprice = this.price * ((100 - discountPercent) / 100);
            BigDecimal bd = new BigDecimal(newprice);
            BigDecimal roundOff = bd.setScale(2, RoundingMode.HALF_UP);
            return roundOff.floatValue();
        }else{
            return this.price;
        }
    }


        public float getSubTotal() {
            return getDetailPrice() * this.quantity;
        }


}
