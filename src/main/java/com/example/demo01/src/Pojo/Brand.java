package com.example.demo01.src.Pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Brand {
    @NonNull
    private Integer id;
    private String name;
    private String logo;
    private String parentname;





    public String getLogoPath(){

        if(this.id<0|| this.logo==null){
            return "/image/default.jpeg";
        }
        else{

            return"/brand_logo/"+this.id+"/"+this.logo;
        }
    }
}
