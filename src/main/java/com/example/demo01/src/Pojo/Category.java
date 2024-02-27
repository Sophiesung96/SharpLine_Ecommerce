package com.example.demo01.src.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    private Integer id;
    private String name;
    private String nickname;
    private String image;
    private Integer enabled;
    private Integer parentid;
    private String parentname;
    private Integer level;


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
        if (this.image == null) {
            return "/image/thumbnail.jpeg";
        }
        return "/category-image/"  + this.id + "/" + this.image;
    }

}






