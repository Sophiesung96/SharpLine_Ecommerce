package com.example.demo01.src.Pojo;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewVote {

    private int id;
    private int reviewId;
    private int customerId;
    private int votes;
    private static final int VOTE_UP_POINT=1;
    private static final int VOTE_DOWN_POINT=-1;


    public ReviewVote(int reviewId) {
        this.reviewId = reviewId;
    }

    public ReviewVote(int reviewId, int customerId, int votes) {
        this.reviewId = reviewId;
        this.customerId = customerId;
        this.votes = votes;
    }

  public boolean IsVotedUp(){
        return this.votes==VOTE_UP_POINT;
  }

  public boolean IsVotedDown(){
        return this.votes==VOTE_DOWN_POINT;
  }


    public void VoteUp(){
       this.votes=VOTE_UP_POINT;
    }

    public void VoteDown(){
        this.votes=VOTE_DOWN_POINT;
    }

}


