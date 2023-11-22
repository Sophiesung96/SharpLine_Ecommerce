package com.example.demo01.src.Pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Country {

    private int id;
    @NonNull
    private String name;
    @NonNull
    private String code;

    public Country(@NonNull String name) {
        this.name = name;
    }
}
