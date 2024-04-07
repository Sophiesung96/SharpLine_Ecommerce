package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.Product;
import com.example.demo01.src.Pojo.Review;
import com.example.demo01.src.Service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReviewDAOImplTest {

    @SpyBean
    ReviewDAO reviewDAO;
    @Autowired
    ReviewService reviewService;


    @Test
    public void testGetReviewList(){
       List<Review> list= reviewDAO.getAllReviewList();
       list.stream().forEach(review-> System.out.println(review));
    }

    @Test
    public void testGetReviewListForCustomer(){
        List<Review> list= reviewDAO.getAllReviewListWithCustomerFullName();
        list.stream().forEach(review-> System.out.println(review));
    }
    @Test
    public void testgetReviewDetailById(){
        int id=1;
         Review review=reviewDAO.getReviewDetailById(1);
         assertEquals("Samsung Galaxy A31",review.getProductName());
    }

    @Test
    @Transactional
    public void testDeleteReviewById(){
        int id=1;
        reviewDAO.DeleteReviewById(id);
       Review review= reviewDAO.getEditReviewById(id);
       assertNull(review);
    }
    @Test
    public void testFind3MostRecentREviews(){
        Product product=new Product();
        product.setId(1);
        List<Review>list=reviewDAO.List3MostRecentReviews(product.getId());
        assertEquals(1,list.get(0).getProductId());
        for(Review review:list){
            assertEquals(4.5,review.getAverageRating());
        }
    }
    @Test
    public void testListAllReviewListByPage(){
        Product product=new Product();
        product.setId(1);
        List<Review>list=reviewDAO.ListAllReviewListByPage(product,1);
      list.stream().forEach(review->assertEquals(review.getProductId(),product.getId()));
    }
    @Test
    public void testCountReviewMadeByCustomerByProductIdnCustomerId(){
        int productId=5;
        int customerId=2;
      Integer num=  reviewDAO.CountReviewMadeByCustomerByProductIdnCustomerId(productId,customerId);
        System.out.println(num);
    }
    @Test
    public void testSearchCustomerReviewByKeyword(){
        String keyword="iphone";
        int customerId=2;
      List<Review>list=reviewDAO.SearchCustomerReviewByKeyword(keyword,customerId);
      list.stream().forEach(review-> assertNotNull(review));
    }
    @Test
    public void testExamineCustomerReviewByProductIdnCustomerId(){
        int productId=5;
        int customerId=2;
        List<Review> list=reviewDAO.ExamineCustomerReviewByProductIdnCustomerId(productId,customerId);
        list.stream().forEach(review->assertEquals(5,review.getProductId()));
    }
    @Test
    @Transactional
    public void testCreateReview(){
        Review review=new Review();
        review.setReviewTime(new Date());
        review.setAverageRating(4.5f);
        review.setProductName("test01");
        review.setProductId(20);
        review.setCustomerId(6);
        review.setComment("testing only");
        review.setHeadline("testtesttest");
       int key= reviewDAO.saveReview(review);
        System.out.println(key);
    }

    @Test
    public void testGetVoteCount(){
        int reviewId=1;
      int reviewCount=reviewDAO.getVoteCount(reviewId);
      assertNotNull(reviewCount);
        System.out.println(reviewCount);
    }

    @Test
    public void testListAllReviewWithMostVoted(){
        int productId=2;
        List<Review> reviewList=reviewDAO.ListAllReviewWithMostVoted(productId);
        assertNotNull(reviewList);
        reviewList.stream().forEach(review-> System.out.println(review.getReviewCount()));
    }




}