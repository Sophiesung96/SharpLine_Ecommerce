package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.CartItem;
import com.example.demo01.src.Pojo.CartItemPName;
import com.example.demo01.src.Pojo.Customer;
import com.example.demo01.src.Pojo.Product;

import java.util.List;

public interface CartItemDao {

    void SaveCartItem(CartItem item);

List<CartItem> findByCustomer(Customer customer);

CartItem findByCustomerAndProduct(Customer customer, Product product);
    void DeleteByCustomerAndProduct(int customerId, int productId);
    void updateQuantity(int quantity,int customerId, int productId);
    List<CartItemPName> getJoinedProductnCustomer(Customer customer);
    void removeProduct(int customerId,int productId);
}
