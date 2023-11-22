package com.example.demo01.src.Service;

import com.example.springboot_ecommerce.Pojo.CartItem;
import com.example.springboot_ecommerce.Pojo.CartItemPName;
import com.example.springboot_ecommerce.Pojo.CheckOutInfo;
import com.example.springboot_ecommerce.Pojo.ShippingRate;

import java.util.List;

public interface CheckOutService {

    public CheckOutInfo preparecheckOut(List<CartItemPName> cartItems, ShippingRate shippingRate);

}
