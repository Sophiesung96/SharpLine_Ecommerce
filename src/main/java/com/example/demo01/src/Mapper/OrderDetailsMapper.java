package com.example.demo01.src.Mapper;
import com.example.springboot_ecommerce.Pojo.Order;
import com.example.springboot_ecommerce.Pojo.OrderDetails;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
public class OrderDetailsMapper implements RowMapper<OrderDetails> {
    @Override
    public OrderDetails mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderDetails orderdetails=new OrderDetails();
        orderdetails.setId(resultSet.getInt("id"));
        orderdetails.setProductId(resultSet.getInt("customer_id"));
        orderdetails.setOrderId(resultSet.getInt("product_id"));
        orderdetails.setProductCost(resultSet.getFloat("product_cost"));
        orderdetails.setQuantity(resultSet.getInt("quantity"));
        orderdetails.setUnitPrice(resultSet.getFloat("unit_price"));
        orderdetails.setShippingCost(resultSet.getFloat("shipping_cost"));
        orderdetails.setSubTotal(resultSet.getFloat("subtotal"));
        return orderdetails;
    }
    }
