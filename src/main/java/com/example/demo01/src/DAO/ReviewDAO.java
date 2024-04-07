package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.Product;
import com.example.demo01.src.Pojo.Review;

import java.util.List;

public interface ReviewDAO {
     List<Review> getAllReviewList();
     List<Review> getAllReviewListWithCustomerFullName();
     Review getEditReviewById(int id);
     void EditReviewById(Review review);
     Review getReviewDetailById(int id);
     void DeleteReviewById(int id);
     List<Review> getAllReviewListForCustomer(int customerId);
     void SaveReview(Review review);
     List<Review> List3MostRecentReviews(int productId);
     List<Review> ListAllReviewListByPage(Product product,int pageNo);
     Integer CountReviewMadeByCustomerByProductIdnCustomerId(int productId,int customerId);
     List<Review> SearchCustomerReviewByKeyword(String keyWord,int customerId);
     List<Review> ExamineCustomerReviewByProductIdnCustomerId(int productId,int customerId);
     int saveReview(Review review);
     int getVoteCount(int reviewId);
     List<Review> ListAllReviewWithMostVoted(int productId);


}
