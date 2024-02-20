package com.example.demo01.src.Service;

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

}
