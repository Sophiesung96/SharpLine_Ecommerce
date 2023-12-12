package com.example.demo01.src.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderTrack {

    private int id;
    private int orderId;
    private String status;
    private Date updatedTime;
    private String notes;
}
