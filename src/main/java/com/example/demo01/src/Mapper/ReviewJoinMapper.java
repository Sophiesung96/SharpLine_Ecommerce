package com.example.demo01.src.Mapper;

import com.example.demo01.src.Pojo.Review;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewJoinMapper implements RowMapper<Review> {
    @Override
    public Review mapRow(ResultSet resultSet, int i) throws SQLException {
                Review review=new Review();
                review.setId(resultSet.getInt("id"));
                review.setAverageRating(resultSet.getFloat("averageRating"));
                review.setProductId(resultSet.getInt("productId"));
                review.setCustomerId(resultSet.getInt("customerId"));
                review.setReviewTime(resultSet.getTimestamp("reviewTime"));
                review.setComment(resultSet.getString("comment"));
                review.setRating(resultSet.getInt("rating"));
                review.setHeadline(resultSet.getString("headline"));
                 review.setCustomerName(resultSet.getString("CustomerName"));
                 review.setProductName(resultSet.getString("productName"));
                return review;
            }
        }

