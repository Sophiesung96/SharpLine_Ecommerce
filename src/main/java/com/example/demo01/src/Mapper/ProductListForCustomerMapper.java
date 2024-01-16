package com.example.demo01.src.Mapper;

import com.example.demo01.src.Pojo.ProductListForCustomer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductListForCustomerMapper implements RowMapper<ProductListForCustomer> {
    @Override
    public ProductListForCustomer mapRow(ResultSet resultSet, int i) throws SQLException {
        ProductListForCustomer product=new ProductListForCustomer();
        product.setOrderId(resultSet.getInt("OrderId"));
        product.setProductName(resultSet.getString("ProductName"));
        product.setQuantity(resultSet.getString("Quantity"));
        product.setSubtotal(resultSet.getString("Subtotal"));
        product.setMainImage(resultSet.getString("MainImage"));
        product.setUnitprice(resultSet.getString("Unitprice"));
        product.setProductId(resultSet.getString("ProductId"));
        return product;
    }
}
