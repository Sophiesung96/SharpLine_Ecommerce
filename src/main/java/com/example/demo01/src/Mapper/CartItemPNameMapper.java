package com.example.demo01.src.Mapper;

import com.example.demo01.src.Pojo.CartItemPName;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartItemPNameMapper implements RowMapper<CartItemPName> {
    @Override
    public CartItemPName mapRow(ResultSet resultSet, int i) throws SQLException {
        CartItemPName cartItemPName=new CartItemPName();
        cartItemPName.setProductId(resultSet.getInt("productId"));
        cartItemPName.setProductName(resultSet.getString("productName"));
        cartItemPName.setProductAlias(resultSet.getString("productAlias"));
        cartItemPName.setProductImage(resultSet.getString("productImage"));
        cartItemPName.setPrice(resultSet.getFloat("price"));
        cartItemPName.setDiscountPercent(resultSet.getFloat("discountPercent"));
        cartItemPName.setQuantity(resultSet.getInt("quantity"));
        cartItemPName.setHeight(resultSet.getFloat("height"));
        cartItemPName.setWeight(resultSet.getFloat("weight"));
        cartItemPName.setLength(resultSet.getFloat("length"));
        cartItemPName.setWidth(resultSet.getFloat("width"));
        cartItemPName.setShippingCost(resultSet.getFloat("shippingCost"));
        return cartItemPName;
    }
}
