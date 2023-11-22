package com.example.demo01.src.Mapper;

import com.example.springboot_ecommerce.Pojo.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class OrderMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
        Order order=new Order();
        order.setId(resultSet.getInt("id"));
        order.setCustomerId(resultSet.getInt("customer_id"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Customize the format
        String formattedDate = sdf.format(resultSet.getTimestamp("order_time"));
        order.setOrderTime(formattedDate);
        order.setPaymentMethod(resultSet.getString("payment_method"));
        order.setProductCost(resultSet.getFloat("product_cost"));
        order.setShippingCost(resultSet.getFloat("shipping_cost"));
        order.setSubTotal(resultSet.getFloat("subtotal"));
        order.setTax(resultSet.getFloat("tax"));
        order.setTotal(resultSet.getFloat("total"));
        order.setStatus(resultSet.getString("status"));
        order.setFirstName(resultSet.getString("first_name"));
        order.setLastName(resultSet.getString("last_name"));
        order.setPhoneNumber(resultSet.getString("phone_number"));
        order.setAddressline1(resultSet.getString("address_line1"));
        order.setAddressline2(resultSet.getString("address_line2"));
        order.setCity(resultSet.getString("city"));
        order.setState(resultSet.getString("state"));
        order.setCountry(resultSet.getString("country"));
        order.setPostalCode(resultSet.getString("postal_code"));
        order.setDeliverDays(resultSet.getInt("deliver_days"));
        order.setDeliverDate(resultSet.getDate("deliver_date"));
        return order;
    }
}
