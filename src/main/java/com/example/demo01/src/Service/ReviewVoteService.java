package com.example.demo01.src.Service;

import com.example.demo01.src.Pojo.*;

import java.util.List;

public interface ReviewVoteService {

    VoteResult unDoVote(ReviewVote reviewVote, Integer reviewId, VoteType voteType);
    VoteResult DoVote(Integer reviewId, Customer customer, VoteType voteType);
    ReviewVote findByProductAndCustomer(int productId, int customerId);
    boolean markReviewVotedForProductByCustomer(List<Review>reviewList, Integer productId, Integer customerId);
}
