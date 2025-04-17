package com.example.demo01.src.Mapper;

import com.example.demo01.src.Pojo.OrderDetails;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountCustomerOrderDetailMapper implements RowMapper<OrderDetails> {
    @Override
    public OrderDetails mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderDetails orderDetails=new OrderDetails();
       orderDetails.setCount(resultSet.getInt("ReviewNum"));
        return orderDetails;
    }
}
