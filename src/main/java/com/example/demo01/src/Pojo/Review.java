package com.example.demo01.src.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private int id;
    private String headline;
    private String comment;
    private float rating;
    private int productId;
    private int customerId;
    private Date reviewTime;
    private String productName;
    private String CustomerName;
    private float averageRating;
    private int reviewCount;
    private int votes;
    private boolean upvotedByCurrentCustomer;
    private boolean downvotedByCurrentCustomer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id == review.id && Float.compare(review.rating, rating) == 0 && productId == review.productId && customerId == review.customerId && Float.compare(review.averageRating, averageRating) == 0 && reviewCount == review.reviewCount && votes == review.votes && upvotedByCurrentCustomer == review.upvotedByCurrentCustomer && downvotedByCurrentCustomer == review.downvotedByCurrentCustomer && Objects.equals(headline, review.headline) && Objects.equals(comment, review.comment) && Objects.equals(reviewTime, review.reviewTime) && Objects.equals(productName, review.productName) && Objects.equals(CustomerName, review.CustomerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, headline, comment, rating, productId, customerId, reviewTime, productName, CustomerName, averageRating, reviewCount, votes, upvotedByCurrentCustomer, downvotedByCurrentCustomer);
    }
}

