package com.example.demo01.src.Controller;

import com.example.demo01.src.Configuration.Utils.ControllerHelper;
import com.example.demo01.src.Exception.CustomerNotFoundException;
import com.example.demo01.src.Pojo.Customer;
import com.example.demo01.src.Pojo.VoteResult;
import com.example.demo01.src.Pojo.VoteType;
import com.example.demo01.src.Service.ReviewVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ReviewVoteRestController {
    @Autowired
    ReviewVoteService reviewVoteService;
    @Autowired
    ControllerHelper controllerHelper;

    @PostMapping("/vote_review/{id}/{type}")
    public VoteResult voteReview(@PathVariable int id, @PathVariable String type,
                                 HttpServletRequest request){
         Customer customer=controllerHelper.getAuthenticatedCustomerForReviewVote(request);
         if(customer ==null){
             return VoteResult.fail("You must login to vote the review!");
         }
        VoteType voteType=VoteType.valueOf(type.toUpperCase());

        return reviewVoteService.DoVote(id,customer,voteType);
    }



}
