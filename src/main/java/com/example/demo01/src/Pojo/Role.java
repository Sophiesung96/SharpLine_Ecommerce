package com.example.demo01.src.Pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Role {
    @NonNull
    int id;
    String name;
    String description;



    public Role(String name) {
        this.name = name;
    }


}
