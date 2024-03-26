package com.example.demo01.src.DAO;

import com.example.demo01.src.Mapper.ReviewVoteMapper;
import com.example.demo01.src.Pojo.ReviewVote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReviewVoteDAOImpl implements  ReviewVoteDAO{

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;




    @Override
    public ReviewVote findByReviewAndCustomer(int reviewId, int customerId) {
        String sql="select * from review_votes where review_id=:reviewid and customer_id=:customerid";
        Map<String,Object> map=new HashMap<>();
        map.put("reviewid",reviewId);
        map.put("customerid",customerId);
        List<ReviewVote>list=namedParameterJdbcTemplate.query(sql,map,new ReviewVoteMapper());
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<ReviewVote> findByProductAndCustomer(int productId, int customerId) {
        String sql="select * from review_votes  votes inner join reviews r on votes.review_id = r.id" +
                "    where r.product_Id=:productId and r.customer_Id=:customerId";
        Map<String,Object> map=new HashMap<>();
        map.put("productId",productId);
        map.put("customerId",customerId);
        List<ReviewVote>list=namedParameterJdbcTemplate.query(sql,map,new ReviewVoteMapper());
        if(list.size()>0){
            return list;
        }
        return null;
    }


    @Override
    public void CreateNewReviewVote(ReviewVote reviewVote) {
        String sql="insert into review_votes(review_id,customer_id,votes) values(:reviewId,:customerid,:votes)";
        Map<String,Object> map=new HashMap<>();
        map.put("reviewId",reviewVote.getReviewId());
        map.put("customerid",reviewVote.getCustomerId());
        map.put("votes",reviewVote.getVotes());
        KeyHolder keyHolder=new GeneratedKeyHolder();
       namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
    }

    @Override
    public void UpdateVoteCount(ReviewVote reviewVote) {
        //COALESCE() in sql is to retrieve the first value that is not null
        //So by writing this, we can set a default value as 0
        // if there's nothing shown in the review_votes table with the specified review_id
        String sql="update reviews set votes=coalesce ((select sum(votes) from review_votes where review_id=:id),0) where id=:id";
        Map<String,Object> map=new HashMap<>();
        map.put("id",reviewVote.getReviewId());
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void delete(ReviewVote vote) {
        String sql="delete from review_votes where id =:id";
        Map<String,Object> map=new HashMap<>();
        map.put("id",vote.getId());
        namedParameterJdbcTemplate.update(sql,map);
    }
}
