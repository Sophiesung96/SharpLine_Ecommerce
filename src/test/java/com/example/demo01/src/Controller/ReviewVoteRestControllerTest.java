package com.example.demo01.src.Controller;

import com.example.demo01.src.DAO.ReviewDAO;
import com.example.demo01.src.DAO.ReviewVoteDAO;
import com.example.demo01.src.Pojo.Review;
import com.example.demo01.src.Pojo.ReviewVote;
import com.example.demo01.src.Pojo.VoteResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewVoteRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ReviewDAO reviewDAO;
    @Autowired
    private ReviewVoteDAO reviewVoteDAO;

    @Test
    @WithUserDetails("Sanya")
    public void tesVoteNotLogin() throws Exception {

      String requestURL="/vote_review/1/up";
      MvcResult mvcResult= mockMvc.perform(post(requestURL).with(csrf()))
               .andDo(print())
               .andReturn();
      String json=mvcResult.getResponse().getContentAsString();
        VoteResult voteResult=objectMapper.readValue(json,VoteResult.class);
        assertNotNull(voteResult);
        System.out.println(voteResult.getMessage());
    }


    @Test
    @WithUserDetails("Sanya")
    public void tesUnDoVoteUp() throws Exception {

        int reviweId=3;
        Review review=reviewDAO.getEditReviewById(reviweId);
        int reviewBeforeCount=review.getReviewCount();
        String requestURL="/vote_review/"+reviweId+"/up";
        MvcResult mvcResult= mockMvc.perform(post(requestURL).with(csrf()))
                .andDo(print())
                .andReturn();
        String json=mvcResult.getResponse().getContentAsString();
        VoteResult voteResult=objectMapper.readValue(json,VoteResult.class);
        assertNotNull(voteResult);
        assertNotEquals(reviewBeforeCount,voteResult.getVoteCount());
        System.out.println(voteResult.getMessage());
    }

    @Test
    public void test(){
        ReviewVote reviewVote=reviewVoteDAO.findByReviewAndCustomer(3,2);
        System.out.println(reviewVote.IsVotedUp());
    }





}