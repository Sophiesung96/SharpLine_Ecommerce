package com.example.demo01.src.Mapper;

import com.example.demo01.src.Pojo.ReviewVote;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewVoteMapper implements RowMapper<ReviewVote> {
    @Override
    public ReviewVote mapRow(ResultSet resultSet, int i) throws SQLException {
        ReviewVote reviewVote=new ReviewVote();
        reviewVote.setReviewId(resultSet.getInt("review_id"));
        reviewVote.setCustomerId(resultSet.getInt("customer_id"));
        reviewVote.setId(resultSet.getInt("id"));
        reviewVote.setVotes(resultSet.getInt("votes"));
        return reviewVote;
    }
}
