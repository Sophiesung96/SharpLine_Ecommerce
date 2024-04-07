package com.example.demo01.src.Mapper;

import com.example.demo01.src.Pojo.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NestedCategoryMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet resultSet, int i) throws SQLException {
        Category category=new Category();
        category.setId(resultSet.getInt("id"));
        category.setName(resultSet.getString("name"));
        category.setParentid(resultSet.getInt("parent_id"));
        category.setLevel(resultSet.getInt("level"));
        return category;
    }
}
