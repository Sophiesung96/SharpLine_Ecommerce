package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.ReviewDAO;
import com.example.demo01.src.DAO.ReviewVoteDAO;
import com.example.demo01.src.Pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        //update reviewDao as well
        reviewVoteDAO.UpdateVoteCount(reviewVote);
       int voteCountNum=reviewDAO.getVoteCount(reviewId);
        return VoteResult.success("You have unvoted "+voteType+" that review",voteCountNum);
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
            // if the review has been voted,and it's a Up Vote,but now you click on the voting again
            // or the review has been voted,and it's a Down Vote,but now you click on the voting again
            if(reviewVote.isVotedUp()&& voteType.equals(VoteType.UP)
                    || reviewVote.isVotedDown()&& voteType.equals(VoteType.DOWN)){
                //unDoVote -> means you want to remove this vote that you created before
              return unDoVote(reviewVote,reviewId,voteType);
           //Your previous vote is good,but now you think the product you are reviewing is bad
                // (change your previous voting decision)
            }else if (reviewVote.isVotedUp()&& voteType.equals(VoteType.DOWN)) {
                   reviewVote.VoteDown();
           //Your previous vote is bad,but now you think the product you are reviewing is good
                // (change your previous voting decision)
            }else if (reviewVote.isVotedDown()&& voteType.equals(VoteType.UP)) {
                reviewVote.VoteUp();
            }
        }
        //This voting for the review has never been made before
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
        return VoteResult.success("You have successfully voted "+voteType+" that review."
                ,voteCountNum);
    }

    @Override
    public ReviewVote findByProductAndCustomer(int productId, int customerId) {
        ReviewVote reviewVote=reviewVoteDAO.findByReviewAndCustomer(productId, customerId);
        return reviewVote;
    }


    @Override
    public boolean markReviewVotedForProductByCustomer(List<Review> reviewList, Integer productId, Integer customerId) {
        List<ReviewVote> list=reviewVoteDAO.findByProductAndCustomer(productId,customerId);
        for(ReviewVote reviewVote:list){
            int reviewId=reviewVote.getReviewId();
            Review DBreview=reviewDAO.getEditReviewById(reviewId);
            if(reviewList.contains(DBreview)){
                int index=reviewList.indexOf(DBreview);
                Review review=reviewList.get(index);
                if(reviewVote.isVotedUp()){
                    // Mark this review as true for it is upvoted by a specified customer
                    review.setUpvotedByCurrentCustomer(true);
                    return true;
                    // Mark this review as true for it is downvoted by a specified customer
                }else if(reviewVote.isVotedDown()){
                    review.setDownvotedByCurrentCustomer(true);
                    return true;
                }
            }
        }
        return false;
    }


}
