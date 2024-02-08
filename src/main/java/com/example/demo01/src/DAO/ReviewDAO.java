package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.Review;

import java.util.List;

public interface ReviewDAO {
     List<Review> getAllReviewList();
     List<Review> getAllReviewListWithCustomerFullName();
     Review getEditReviewById(int id);
     void EditReviewById(Review review);
     Review getReviewDetailById(int id);

}
