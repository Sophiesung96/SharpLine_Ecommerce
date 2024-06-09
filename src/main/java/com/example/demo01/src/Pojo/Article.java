package com.example.demo01.src.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    private int id;
    private String title;
    private String type;
    private String createdBy;
    private String updatedTime;
    private int published;
    private String alias;
    private String content;
}
