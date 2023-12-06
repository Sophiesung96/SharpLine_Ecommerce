package com.example.demo01.src.Mapper;

import com.example.demo01.src.Pojo.BrandCategoryName;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BrandCategoryNameMapper implements RowMapper<BrandCategoryName> {
    @Override
    public BrandCategoryName mapRow(ResultSet resultSet, int i) throws SQLException {
        BrandCategoryName brand = new BrandCategoryName();
        brand.setName(resultSet.getString("cname"));
        brand.setId(resultSet.getInt("cid"));
        return brand;
    }
}
