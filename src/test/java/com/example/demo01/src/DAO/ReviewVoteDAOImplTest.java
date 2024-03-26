package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.ReviewVote;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReviewVoteDAOImplTest {

    @SpyBean
    ReviewVoteDAO reviewVoteDAO;

    @Test
    public void testCreateNewReviewVote(){
        int reviewId=2;
        int customerId=2;
        int votes=5;
        ReviewVote reviewVote=new ReviewVote(reviewId,customerId,votes);
        reviewVoteDAO.CreateNewReviewVote(reviewVote);
        ReviewVote DBReviewVote=reviewVoteDAO.findByReviewAndCustomer(reviewId,customerId);
        assertEquals(DBReviewVote.getVotes(),reviewVote.getVotes());

    }

    @Test
    public void testfindByProductAndCustomer(){
        int reviewId=2;
        int customerId=2;
        int productId=5;
       List<ReviewVote> list= reviewVoteDAO.findByProductAndCustomer(productId,customerId);
       assertNotNull(list);
       assertTrue(list.size()>0);
    }
    @Test
    public void testUpdateVoteCount(){
        int reviewId=2;
        ReviewVote reviewVote=new ReviewVote(reviewId);
        reviewVoteDAO.UpdateVoteCount(reviewVote);
    }

}