package com.example.demo01.src.Service;

import com.example.demo01.src.Pojo.Customer;
import com.example.demo01.src.Pojo.Product;
import com.example.demo01.src.Pojo.Review;

import java.util.List;

public interface ReviewService {
     List<Review> getAllReviewList();
     List<Review>getAllReviewListWithCustomerFullName();
     Review getEditReviewById(int id);
     void EditReviewById(Review review);
     Review getReviewDetailById(int id);
     void DeleteReviewById(int id);
     List<Review> getAllReviewListForCustomer(int customerId);
     void SaveReview(Review review);
     List<Review> List3MostRecentReviews(int productId);
      List<Review> ListAllReviewListByPage(Product product, int pageNo);
      boolean didCustomerReviewProductBefore(int customerId, int productId);
     List<Review> SearchCustomerReviewByKeyword(String keyWord,int customerId);
     List<Review> ExamineCustomerReviewByProductIdnCustomerId(int productId,int customerId);
      boolean canCustomerReviewProduct(int productId,int customerId);
     Review saveReview(Review review);

}
