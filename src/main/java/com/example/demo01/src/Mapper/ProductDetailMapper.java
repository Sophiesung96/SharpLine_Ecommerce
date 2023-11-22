package com.example.demo01.src.Mapper;

import com.example.springboot_ecommerce.Pojo.ProductDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDetailMapper implements RowMapper<ProductDetail> {
    @Override
    public ProductDetail mapRow(ResultSet resultSet, int i) throws SQLException {
        ProductDetail productDetail=new ProductDetail();
        productDetail.setId(resultSet.getInt("id"));
        productDetail.setName(resultSet.getString("name"));
        productDetail.setValue(resultSet.getString("value"));
        productDetail.setProductId(resultSet.getInt("product_id"));
        return productDetail;
    }
}
