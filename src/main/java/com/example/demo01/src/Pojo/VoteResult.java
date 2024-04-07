package com.example.demo01.src.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteResult {
    boolean successful;
    String message;
    int voteCount;

    public static VoteResult fail(String message){
        return new VoteResult(false,message,0);
    }
    public static VoteResult success(String message,int voteCount){
        return new VoteResult(true,message,voteCount);
    }
    public boolean IsSuccessful(){
        return successful;
    }
}
