package com.example.demo01.src.Service;

import com.example.springboot_ecommerce.Pojo.CartItem;
import com.example.springboot_ecommerce.Pojo.CartItemPName;
import com.example.springboot_ecommerce.Pojo.Customer;

import java.util.List;

public interface ShoppingCartService {

    public Integer addProduct(Integer productId, Integer quantity, Customer customer);
    public void  deleteByCustomer(Customer customer,int productId);
    List<CartItemPName> getJoinedProductnCustomer(Customer customer);
    List<CartItem> listAllCartItem(Customer customer);


}
