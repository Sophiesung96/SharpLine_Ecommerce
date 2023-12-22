package com.example.demo01.src.Mapper;

import com.example.demo01.src.Pojo.ProductImage;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductImageMapper implements RowMapper<ProductImage> {
    @Override
    public ProductImage mapRow(ResultSet resultSet, int i) throws SQLException {
        ProductImage productImage=new ProductImage();
        productImage.setId(resultSet.getInt("id"));
        productImage.setProductId(resultSet.getInt("product_id"));
        productImage.setName(resultSet.getString("name"));
        return productImage;
    }
}
