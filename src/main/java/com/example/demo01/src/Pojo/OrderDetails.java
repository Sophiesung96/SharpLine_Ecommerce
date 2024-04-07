package com.example.demo01.src.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {
    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private float unitPrice;
    private float subTotal;
    private float productCost;
    private float shippingCost;
    private int Count;


}
