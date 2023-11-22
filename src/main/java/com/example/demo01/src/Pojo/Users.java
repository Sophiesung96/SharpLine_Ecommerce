package com.example.demo01.src.Pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Users {

    int id;
    String Email;
    String enabledStatus;
    String first_name;
    String last_name;
    String password;
    String photo;
    String usersRole;
    String keyword;




    public Users(int id, String email, String enabledStatus, String first_name, String last_name, String password) {
        this.id = id;
        Email = email;
        this.enabledStatus = enabledStatus;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
    }

    public Users(int id, String email) {
        this.id = id;
        Email = email;
    }


    public String getImagePath(){
        if(this.id<0|| this.photo==null){
            return "/image/thubnail.jpeg";
        }
        else{

            return"/user-pics"+this.id+"/"+this.photo;
        }
    }
}
