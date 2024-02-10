package com.example.demo01.src.DAO;

import com.example.demo01.src.Mapper.ReviewJoinMapper;
import com.example.demo01.src.Mapper.ReviewMapper;
import com.example.demo01.src.Pojo.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ReviewDAOImpl implements  ReviewDAO{

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Review> getAllReviewList() {
        String sql="select * from reviews";
        Map<String, Object> map=new HashMap<>();
        List<Review>list=new ArrayList<>();
        list=namedParameterJdbcTemplate.query(sql,map,new ReviewMapper());
        if(list.size()>0){
            return list;
        }
        return null;
    }

    @Override
    public List<Review> getAllReviewListWithCustomerFullName() {
        String sql="select r.id as id,r.comment as comment, r.customer_Id as customerId, r.headline as headline,r.product_Id as productId," +
                "     r.rating as rating, r.review_time as reviewTime,concat(c.first_name,' ',c.last_name) as CustomerName," +
                "    p.name as productName from reviews r inner join customers  c on r.customer_Id=c.id" +
                "   inner join products p on r.product_Id=p.id ";
        Map<String, Object> map=new HashMap<>();
        List<Review>list=new ArrayList<>();
        list=namedParameterJdbcTemplate.query(sql,map,new ReviewJoinMapper());
        if(list.size()>0){
            return list;
        }
        return null;
    }

    @Override
    public Review getEditReviewById(int id) {
        String sql="select * from reviews where id=:id";
        Map<String, Object> map=new HashMap<>();
        map.put("id",id);
        List<Review>list=new ArrayList<>();
        list=namedParameterJdbcTemplate.query(sql,map,new ReviewMapper());
        if(list.size()>0){
            return list.get(0);
        }

        return null;
    }

    @Override
    public void EditReviewById(Review review) {
        String sql="update reviews set headline=:headline, comment =:comment where id=:id";
        Map<String, Object> map=new HashMap<>();
        map.put("id",review.getId());
        map.put("comment",review.getComment());
        map.put("headline",review.getHeadline());
        namedParameterJdbcTemplate.update(sql,map);

    }

    @Override
    public Review getReviewDetailById(int id) {
        String sql="select r.id as id,r.comment as comment, r.customer_Id as customerId, r.headline as headline,r.product_Id as productId," +
                "     r.rating as rating, r.review_time as reviewTime,concat(c.first_name,' ',c.last_name) as CustomerName," +
                "    p.name as productName from reviews r inner join customers  c on r.customer_Id=c.id" +
                "   inner join products p on r.product_Id=p.id where r.id=:id ";
        Map<String, Object> map=new HashMap<>();
        map.put("id",id);
        List<Review>list=new ArrayList<>();
        list=namedParameterJdbcTemplate.query(sql,map,new ReviewJoinMapper());
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public void DeleteReviewById(int id) {
        String sql="Delete from reviews where id=:id";
        Map<String, Object> map=new HashMap<>();
        map.put("id",id);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public List<Review> getAllReviewListForCustomer(int customerId) {

        String sql="select r.id as id,r.comment as comment, r.customer_Id as customerId, r.headline as headline,r.product_Id as productId," +
                "     r.rating as rating, r.review_time as reviewTime,concat(c.first_name,' ',c.last_name) as CustomerName," +
                "    p.name as productName from reviews r inner join customers  c on r.customer_Id=c.id" +
                "   inner join products p on r.product_Id=p.id where r.customer_Id=:customerId";
        Map<String, Object> map=new HashMap<>();
        map.put("customerId",customerId);
        List<Review>list=new ArrayList<>();
        list=namedParameterJdbcTemplate.query(sql,map,new ReviewJoinMapper());
        if(list.size()>0){
            return list;
        }
        return null;
    }
}

