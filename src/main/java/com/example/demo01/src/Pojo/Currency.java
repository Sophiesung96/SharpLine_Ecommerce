package com.example.demo01.src.Pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;

@Data
@NoArgsConstructor
public class Currency implements Serializable {
    private int id;
    private String name;
    private String code;
    private String symbol;


    @Override
    public String toString() {
        return name  + "-" + code + "-"+symbol;
    }


}
