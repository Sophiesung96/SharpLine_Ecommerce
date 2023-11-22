package com.example.demo01.src.Pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CartItem {
    private int id;
    private int customer;
    private int product;
    private int quantity;
    private float shippingCost;
    private List<CartItemPName> list;


}
