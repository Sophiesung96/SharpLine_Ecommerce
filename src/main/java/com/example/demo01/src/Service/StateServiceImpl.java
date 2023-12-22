package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.StateDao;
import com.example.demo01.src.Pojo.Country;
import com.example.demo01.src.Pojo.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StateServiceImpl implements StateService {

    @Autowired
    StateDao stateDao;

    @Override
    public List<State> findByCountryOrderByNameAsc(int id) {
        List<State> list=stateDao.findByCountryOrderByNameAsc(id);
        return list;
    }

    @Override
    public void save(State state) {
     stateDao.save(state);
    }

    @Override
    public void delete(int id) {
        stateDao.delete(id);
    }

    @Override
    public State getCategoryById(State state) {

        return stateDao.getCategoryById(state);
    }

    @Override
    public List<Country> listAll() {
        List<Country> list=new ArrayList<>();
        list=stateDao.listAll();
        return list;
    }


    @Override
    public void updateStateByCategoryId(State state) {
      stateDao.updateStateByCategoryId(state);
    }
}
