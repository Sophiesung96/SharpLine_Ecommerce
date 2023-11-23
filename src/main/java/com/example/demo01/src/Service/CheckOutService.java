package com.example.demo01.src.Service;

import com.example.demo01.src.Pojo.CartItem;
import com.example.demo01.src.Pojo.CartItemPName;
import com.example.demo01.src.Pojo.CheckOutInfo;
import com.example.demo01.src.Pojo.ShippingRate;

import java.util.List;

public interface CheckOutService {

    public CheckOutInfo preparecheckOut(List<CartItemPName> cartItems, ShippingRate shippingRate);

}
