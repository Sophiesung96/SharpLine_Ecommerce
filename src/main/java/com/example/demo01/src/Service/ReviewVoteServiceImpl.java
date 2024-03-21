package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.ReviewDAO;
import com.example.demo01.src.DAO.ReviewVoteDAO;
import com.example.demo01.src.Pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ReviewVoteServiceImpl implements  ReviewVoteService{
    @Autowired
    ReviewDAO reviewDAO;
    @Autowired
    ReviewVoteDAO reviewVoteDAO;

    @Override
    public VoteResult unDoVote(ReviewVote reviewVote, Integer reviewId, VoteType voteType) {
        reviewVoteDAO.delete(reviewVote);
       int voteCountNum=reviewDAO.getVoteCount(reviewId);
        return VoteResult.success("You have unvoted"+voteType+"that review",voteCountNum);
    }

    @Override
    public VoteResult DoVote(Integer reviewId, Customer customer, VoteType voteType) {
        Review review=null;
        try {
            review = reviewDAO.getEditReviewById(reviewId);
        }catch(NoSuchElementException exception){
            return VoteResult.fail("The reviewID"+reviewId+"no longer exists.");
        }
        ReviewVote reviewVote=reviewVoteDAO.findByReviewAndCustomer(reviewId,customer.getId());
        if(reviewVote!=null){
            // if the review has been voted and it's a Up Vote but now you click on the voting again
            // or the review has been voted and it's a Down Vote but now you click on the voting again
            if(reviewVote.IsVotedUp()&& voteType.equals(VoteType.UP)
                    || reviewVote.IsVotedDown()&& voteType.equals(VoteType.DOWN)){
                //unDoVote -> means you want to vote this review again(change your previous voting decision)
              return unDoVote(reviewVote,reviewId,voteType);
           //Your previous vote is good but now you think the product you are reviewing is bad
            }else if (reviewVote.IsVotedUp()&& voteType.equals(VoteType.DOWN)) {
                   reviewVote.VoteDown();
           //Your previous vote is bad but now you think the product you are reviewing is good
            }else if (reviewVote.IsVotedDown()&& voteType.equals(VoteType.UP)) {
                reviewVote.VoteUp();
            }
        }
        else{
            reviewVote=new ReviewVote();
            reviewVote.setCustomerId(customer.getId());
            reviewVote.setReviewId(reviewId);
            if(voteType.equals(VoteType.UP)){
                reviewVote.VoteUp();
            }else{
                reviewVote.VoteDown();
            }
        }
        reviewVoteDAO.CreateNewReviewVote(reviewVote);
        reviewVoteDAO.UpdateVoteCount(reviewVote);
        int voteCountNum=reviewDAO.getVoteCount(reviewId);
        return VoteResult.success("You have successfully voted"+voteType+"that review."
                ,voteCountNum);
    }
}
