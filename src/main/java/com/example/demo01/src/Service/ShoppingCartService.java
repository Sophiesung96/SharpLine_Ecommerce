package com.example.demo01.src.Service;

import com.example.demo01.src.Pojo.CartItem;
import com.example.demo01.src.Pojo.CartItemPName;
import com.example.demo01.src.Pojo.Customer;

import java.util.List;

public interface ShoppingCartService {

    public Integer addProduct(Integer productId, Integer quantity, Customer customer);
    public void removeProduct(int customerId,int productId);
    List<CartItemPName> getJoinedProductnCustomer(Customer customer);
    List<CartItem> listAllCartItem(Customer customer);

    public float updateQuantity(Integer productId,Integer quantity,Customer customer );


}
