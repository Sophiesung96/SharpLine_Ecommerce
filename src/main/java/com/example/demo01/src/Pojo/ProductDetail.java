package com.example.demo01.src.Pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDetail {

    private int id;
    private String name;
    private String value;
    private Integer productId;



    public ProductDetail(int id,String name, String value) {
        this.name = name;
        this.value = value;
        this.id=id;

    }

    public ProductDetail(String name, String value) {
        this.name = name;
        this.value = value;
    }


}
