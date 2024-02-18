package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.ReviewDAO;
import com.example.demo01.src.Pojo.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewDAO reviewDAO;

    @Override
    public List<Review> getAllReviewList() {
        List<Review> list=new ArrayList<>();
        list=reviewDAO.getAllReviewList();
        return list;
    }

    @Override
    public List<Review> getAllReviewListWithCustomerFullName() {
        List<Review> list=new ArrayList<>();
        list=reviewDAO.getAllReviewListWithCustomerFullName();
        return list;
    }

    @Override
    public Review getEditReviewById(int id) {
        Review review=reviewDAO.getEditReviewById(id);
        return review;
    }

    @Override
    public void EditReviewById(Review review) {
        reviewDAO.EditReviewById(review);
     }

    @Override
    public Review getReviewDetailById(int id) {
        Review review=reviewDAO.getReviewDetailById(id);
        return review;
    }

    @Override
    public void DeleteReviewById(int id) {
        reviewDAO.DeleteReviewById(id);
    }

    @Override
    public List<Review> getAllReviewListForCustomer(int customerId) {
        List<Review> list=reviewDAO.getAllReviewListForCustomer(customerId);
        return list;
    }
}
