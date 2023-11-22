package com.example.demo01.src.Mapper;

import com.example.springboot_ecommerce.Pojo.CartItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartItemMapper implements RowMapper<CartItem> {
    @Override
    public CartItem mapRow(ResultSet resultSet, int i) throws SQLException {
        CartItem cartItem=new CartItem();
        cartItem.setId(resultSet.getInt("id"));
        cartItem.setCustomer(resultSet.getInt("customer_id"));
        cartItem.setProduct(resultSet.getInt("product_id"));
        cartItem.setQuantity(resultSet.getInt("quantity"));

        return cartItem;
    }
}
