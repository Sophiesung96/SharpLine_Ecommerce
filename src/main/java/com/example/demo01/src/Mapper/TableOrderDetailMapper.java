package com.example.demo01.src.Mapper;

import com.example.demo01.src.Pojo.OrderDetailForm;
import com.example.demo01.src.Pojo.TableOrderDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;

public class TableOrderDetailMapper implements RowMapper<TableOrderDetail> {
    @Override
    public TableOrderDetail mapRow(ResultSet resultSet, int i) throws SQLException {
        TableOrderDetail order = new TableOrderDetail();
        order.setCustomerId(resultSet.getInt("customerId"));
        order.setProductId(resultSet.getInt("productId"));
        order.setId(resultSet.getInt("id"));
        order.setAddressline1(resultSet.getString("addressline1"));
        order.setAddressline2(resultSet.getString("addressline2"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(resultSet.getTimestamp("orderTime"));
        order.setOrderTime(formattedDate);
        order.setDetailproductCost(resultSet.getFloat("DetailproductCost"));
        order.setSubTotal(resultSet.getFloat("subTotal"));
        order.setDeliverDays(resultSet.getInt("deliverDays"));
        String deliverDate = sdf.format(resultSet.getDate("deliverDate"));
        order.setDeliverDate(deliverDate);
        order.setProductname(resultSet.getString("Productname"));
        order.setProductmainImage(resultSet.getString("ProductmainImage"));
        order.setProductCost(resultSet.getFloat("productCost"));
        order.setShippingCost(resultSet.getFloat("shippingCost"));
        order.setTax(resultSet.getFloat("tax"));
        order.setStatus(resultSet.getString("status"));
        order.setTotal(resultSet.getFloat("total"));
        order.setEmail(resultSet.getString("email"));
        order.setCity(resultSet.getString("city"));
        order.setState(resultSet.getString("state"));
        order.setCountry(resultSet.getString("country"));
        order.setFirstName(resultSet.getString("firstName"));
        order.setLastName(resultSet.getString("lastName"));
        order.setPostalCode(resultSet.getString("postalCode"));
        order.setPhoneNumber(resultSet.getString("phoneNumber"));
        order.setEnabled(resultSet.getInt("enabled"));
        order.setPaymentMethod(resultSet.getString("paymentmethod"));
        order.setQuantity(resultSet.getInt("quantity"));
        order.setUnitPrice(resultSet.getFloat("unitPrice"));
        order.setDetailproductCost(resultSet.getFloat("DetailproductCost"));
        order.setStatusCondition(resultSet.getString("StatusCondition"));
        order.setCategoryName(resultSet.getString("CategoryName"));
        return order;
    }
}
