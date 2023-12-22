package com.example.demo01.src.Pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Set;
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class State {

    private int id;
    @NonNull
    private String name;
    @NonNull
    private int countryId;
    private Set<State> states;

    public State(int id) {
        this.id = id;
    }






}
