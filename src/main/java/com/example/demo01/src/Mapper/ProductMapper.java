package com.example.demo01.src.Mapper;

import com.example.demo01.src.Pojo.Brand;
import com.example.demo01.src.Pojo.Category;
import com.example.demo01.src.Pojo.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setAlias(resultSet.getString("alias"));
        product.setName(resultSet.getString("name"));
        product.setCategoryId(resultSet.getInt("category_id"));
        product.setBrandId(resultSet.getInt("brand_id"));
        product.setCost(resultSet.getFloat("cost"));
        product.setDiscountPercent(resultSet.getFloat("discount_percent"));
        product.setFullContent(resultSet.getString("full_description"));
        product.setHeight(resultSet.getFloat("height"));
        product.setWidth(resultSet.getFloat("width"));
        product.setPrice(resultSet.getFloat("price"));
        product.setShortDescription(resultSet.getString("short_description"));
        product.setLength(resultSet.getFloat("length"));
        product.setMainimage(resultSet.getString("main_Image"));
        product.setWeight(resultSet.getFloat("weight"));
        product.setEnabled(resultSet.getInt("enabled"));
        product.setCreatedTime(resultSet.getTimestamp("created_Time"));
        product.setUpdatedTime(resultSet.getTimestamp("updated_Time"));
        product.setInStock(resultSet.getInt("in_stock"));
        product.setReview(resultSet.getInt("review_Count"));
        product.setAverageRating(resultSet.getFloat("average_rating"));
        return product;
    }
}
