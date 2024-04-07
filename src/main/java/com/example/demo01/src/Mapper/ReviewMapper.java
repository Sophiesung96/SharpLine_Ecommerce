package com.example.demo01.src.Mapper;

import com.example.demo01.src.Pojo.Review;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewMapper implements RowMapper<Review> {
    @Override
    public Review mapRow(ResultSet resultSet, int i) throws SQLException {
        Review review=new Review();
        review.setId(resultSet.getInt("id"));
        review.setProductId(resultSet.getInt("product_Id"));
        review.setCustomerId(resultSet.getInt("customer_Id"));
        review.setReviewTime(resultSet.getTimestamp("review_time"));
        review.setComment(resultSet.getString("comment"));
        review.setRating(resultSet.getInt("rating"));
        review.setHeadline(resultSet.getString("headline"));
        review.setVotes(resultSet.getInt("votes"));

        return review;
    }
}
