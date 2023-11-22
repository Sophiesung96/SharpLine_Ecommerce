package com.example.demo01.src.Mapper;

import com.example.springboot_ecommerce.Pojo.Brand;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BrandMapper implements RowMapper<Brand> {
    @Override
    public Brand mapRow(ResultSet resultSet, int i) throws SQLException {
        Brand brand = new Brand();
        brand.setId(resultSet.getInt("id"));
        brand.setName(resultSet.getString("name"));
        brand.setLogo(resultSet.getString("logo"));
        return brand;
    }
}
