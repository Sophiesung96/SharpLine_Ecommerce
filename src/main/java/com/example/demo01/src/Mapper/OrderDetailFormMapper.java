package com.example.demo01.src.Mapper;

import com.example.springboot_ecommerce.Pojo.OrderDetailForm;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class OrderDetailFormMapper implements RowMapper<OrderDetailForm> {
    @Override
    public OrderDetailForm mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderDetailForm order=new OrderDetailForm();
        order.setCustomerId(resultSet.getInt("customerId"));
        order.setId(resultSet.getInt("id"));
        order.setAddressline1(resultSet.getString("addressline1"));
        order.setAddressline2(resultSet.getString("addressline2"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(resultSet.getTimestamp("orderTime"));
        order.setOrderTime(formattedDate);
        order.setCity(resultSet.getString("city"));
        order.setState(resultSet.getString("state"));
        order.setCountry(resultSet.getString("country"));
        order.setFirstName(resultSet.getString("firstName"));
        order.setLastName(resultSet.getString("lastName"));
        order.setPostalCode(resultSet.getString("postalCode"));
        order.setPhoneNumber(resultSet.getString("phoneNumber"));
        order.setEnabled(resultSet.getInt("enabled"));
        order.setPaymentMethod(resultSet.getString("paymentmethod"));
        return order;
    }
}
