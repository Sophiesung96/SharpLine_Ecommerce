package com.example.demo01.src.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    private int id;
    private String name;
    private String nickname;
    private String image;
    private int enabled;
    private int parentid;
    private String parentname;
    private int level;
    private Constants constants;


    public Category(int parentid) {
        this.parentid = parentid;

    }

    public Category(String name) {
        this.name = name;
    }

    public Category(Integer id, String name, String nickname) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;

    }



    public String getPhotosImagePaths() {
        this.constants=new Constants();
        if (this.image == null) {
            return "/image/thumbnail.jpeg";
        }
        return constants.getS3BaseUri() +"/categories-images/"+ this.id + "/" + this.image;
    }

}






