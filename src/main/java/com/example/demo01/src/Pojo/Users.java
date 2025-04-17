package com.example.demo01.src.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.NotThreadSafe;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users  {

    private int id;
    private String Email;
    private int    enabled;
    private String first_name;
    private String last_name;
    private String password;
    private String photo;
    private String usersRole;
    private String keyword;
    private Constants constants;




    public Users(int id, String email, int enabled, String first_name, String last_name, String password,Constants constants) {
        this.id = id;
        Email = email;
        this.enabled = enabled;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.constants=new Constants();
    }


    public Users(int id, String email) {
        this.id = id;
        Email = email;
        this.constants=new Constants();
    }

    public Users(int id, String email,String password) {
        this.id = id;
        Email = email;
        this.password=password;
        this.constants=new Constants();
    }




    public String getImagePath(){
        if(this.id<0|| this.photo==null){
            return "/image/thubnail.jpeg";
        }
        else{
            constants=new Constants();
            log.info("AWS URI:{}",constants.getS3BaseUri());
            return constants.getS3BaseUri()+"/user-photos/"+this.id+"/"+this.photo;
        }
    }

    public Collection<String> getRoles() {
        String[] list = {"Admin", "SalesPerson", "Editor", "Shipper", "Assistant"};
       List<String> UsersRoleList= Arrays.asList(list);
        return UsersRoleList;
    }




}
