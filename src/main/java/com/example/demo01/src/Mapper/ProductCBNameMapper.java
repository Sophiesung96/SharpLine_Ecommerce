package com.example.demo01.src.Mapper;

import com.example.springboot_ecommerce.Pojo.ProductCBName;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductCBNameMapper implements RowMapper<ProductCBName> {
    @Override
    public ProductCBName mapRow(ResultSet resultSet, int i) throws SQLException {
        ProductCBName p = new ProductCBName();
        p.setBrandName(resultSet.getString("brand_name"));
        p.setCategoryName(resultSet.getString("category_name"));
        p.setBid(resultSet.getInt("bid"));
        p.setCid(resultSet.getInt("cid"));
        return p;
    }
}
