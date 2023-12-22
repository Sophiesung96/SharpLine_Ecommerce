package com.example.demo01.src.Service;

import com.example.demo01.src.Pojo.Country;
import com.example.demo01.src.Pojo.State;

import java.util.List;

public interface StateService {
    List<Country> listAll();

    List<State> findByCountryOrderByNameAsc(int id);

    public void save(State state);
    public void delete(int id);
    public State getCategoryById(State state);
    public void updateStateByCategoryId(State state);

}
