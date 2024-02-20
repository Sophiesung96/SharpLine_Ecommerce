package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.Product;
import com.example.demo01.src.Pojo.Review;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReviewDAOImplTest {

    @SpyBean
    ReviewDAO reviewDAO;


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
}