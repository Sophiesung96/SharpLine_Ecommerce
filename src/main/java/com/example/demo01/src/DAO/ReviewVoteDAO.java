package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.ReviewVote;

import java.util.List;

public interface ReviewVoteDAO {


     ReviewVote findByReviewAndCustomer(int reviewId, int customerId);
     List<ReviewVote> findByProductAndCustomer(int productId, int customerId);
     void CreateNewReviewVote(ReviewVote reviewVote);
     void UpdateVoteCount(ReviewVote reviewVote);
     void delete(ReviewVote vote);

}
