package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.OrderDetailDAO;
import com.example.demo01.src.DAO.ProductDAO;
import com.example.demo01.src.DAO.ReviewDAO;
import com.example.demo01.src.DAO.ReviewVoteDAO;
import com.example.demo01.src.Pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewDAO reviewDAO;
    @Autowired
    ProductDAO productDAO;

    @Autowired
    OrderDetailDAO orderDetailDAO;

    @Autowired
    ReviewVoteDAO reviewVoteDAO;

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

    @Override
    public void SaveReview(Review review) {
        reviewDAO.SaveReview(review);
        productDAO.UpdateReviewCountandAverageRating(review.getProductId());
    }

    @Override
    public List<Review> List3MostRecentReviews(int productId) {
        List<Review> list=reviewDAO.List3MostRecentReviews(productId);
        return list;
    }

    @Override
    public List<Review> ListAllReviewListByPage(Product product, int pageNo) {
        List<Review>list=reviewDAO.ListAllReviewListByPage(product, pageNo);
        return list;
    }

    @Override
    public boolean canCustomerReviewProduct(int productId, int customerId) {
        List<OrderDetails>list= orderDetailDAO.CountCustomerOrderByProductIdAndOrderStatus(productId, customerId,OrderStatus.DELIVERED);
        return list!=null;
    }

    @Override
    public boolean didCustomerReviewProductBefore(int customerId, int productId) {
        Integer reviewCount= reviewDAO.CountReviewMadeByCustomerByProductIdnCustomerId(productId,customerId);
        return reviewCount>0;
    }

    @Override
    public List<Review> SearchCustomerReviewByKeyword(String keyWord,int customerId) {
        List<Review> list=reviewDAO.SearchCustomerReviewByKeyword(keyWord,customerId);
        return list;
    }

    @Override
    public List<Review> ExamineCustomerReviewByProductIdnCustomerId(int productId, int customerId) {
        List<Review> list=reviewDAO.ExamineCustomerReviewByProductIdnCustomerId(productId, customerId);
        return list;
    }

    @Override
    public Review saveReview(Review review) {
        review.setReviewTime(new Date());
        int reviewId=reviewDAO.saveReview(review);
        Review newlyCreatedReview=reviewDAO.getEditReviewById(reviewId);
        Integer productId= newlyCreatedReview.getProductId();
        productDAO.UpdateReviewCountandAverageRating(productId);
        return newlyCreatedReview;
    }


}
