package com.example.demo01.src.Mapper;

import com.example.demo01.src.Pojo.TableOrderDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class OrderDetailJoinOrderMapper implements RowMapper<TableOrderDetail> {
    @Override
    public TableOrderDetail mapRow(ResultSet resultSet, int i) throws SQLException {
        TableOrderDetail order = new TableOrderDetail();
        order.setCustomerId(resultSet.getInt("customerId"));
        order.setProductIds(resultSet.getString("productIds"));
        order.setId(resultSet.getInt("id"));
        order.setAddressline1(resultSet.getString("addressline1"));
        order.setAddressline2(resultSet.getString("addressline2"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(resultSet.getTimestamp("orderTime"));
        order.setOrderTime(formattedDate);
        order.setDetailproductCosts(resultSet.getString("DetailproductCosts"));
        order.setSubTotals(resultSet.getString("subTotals"));
        order.setDeliverDays(resultSet.getInt("deliverDays"));
        String deliverDate = sdf.format(resultSet.getDate("deliverDate"));
        order.setDeliverDate(deliverDate);
        order.setProductnames(resultSet.getString("ProductNames"));
        order.setProductMainImages(resultSet.getString("ProductMainImage"));
        order.setProductCosts(resultSet.getString("productCosts"));
        order.setShippingCosts(resultSet.getString("ShippingCosts"));
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
        order.setPaymentMethod(resultSet.getString("paymentMethod"));
        order.setQuantities(resultSet.getString("Quantities"));
        order.setUnitPrices(resultSet.getString("UnitPrices"));
        order.setDetailproductCosts(resultSet.getString("DetailproductCosts"));
        order.setStatus(resultSet.getString("status"));
        order.setCategoryNames(resultSet.getString("CategoryNames"));
        return order;
    }
}
