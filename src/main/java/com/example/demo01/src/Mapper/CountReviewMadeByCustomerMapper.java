package com.example.demo01.src.Mapper;

import com.example.demo01.src.Pojo.Review;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountReviewMadeByCustomerMapper implements RowMapper<Review> {

    @Override
    public Review mapRow(ResultSet resultSet, int i) throws SQLException {
        Review review=new Review();
        review.setReviewCount(resultSet.getInt("reviewCount"));
        return review;
    }
}
