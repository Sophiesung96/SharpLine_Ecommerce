package com.example.demo01.src.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private int id;
    private String headline;
    private String comment;
    private int rating;
    private int productId;
    private int customerId;
    private Date reviewTime;
    private String productName;
    private String CustomerName;


}
