package com.example.demo01.src.Service;

import com.example.demo01.src.Pojo.Customer;
import com.example.demo01.src.Pojo.ReviewVote;
import com.example.demo01.src.Pojo.VoteResult;
import com.example.demo01.src.Pojo.VoteType;

public interface ReviewVoteService {

    VoteResult unDoVote(ReviewVote reviewVote, Integer reviewId, VoteType voteType);
    VoteResult DoVote(Integer reviewId, Customer customer, VoteType voteType);
}
