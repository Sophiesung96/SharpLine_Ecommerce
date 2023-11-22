package com.example.demo01.src.DAO;

import com.example.demo01.src.Mapper.CountryMapper;
import com.example.demo01.src.Mapper.StateMapper;
import com.example.demo01.src.Pojo.Country;
import com.example.demo01.src.Pojo.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StateDaoImpl implements StateDao{
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public List<Country> listAll() {
        List<Country> list=new ArrayList<>();
        String sql="select * from countries order by name asc";
        Map<String,Object> map=new HashMap<>();
        list=namedParameterJdbcTemplate.query(sql,map,new CountryMapper());
        if(list.size()>0){
            return list;
        }
        return null;
    }

    @Override
    public List<State> findByCountryOrderByNameAsc(int id) {
        List<State> list=new ArrayList<>();
        String sql="select * from states where country_id=:id order by name asc";
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        list=namedParameterJdbcTemplate.query(sql,map,new StateMapper());
        if(list.size()>0){
            return list;
        }
        return null;
    }

    @Override
    public void save(State state) {
        List<State> list=new ArrayList<>();
        String sql="insert into states(name,country_id) values( :sname,:categoryId)";
        Map<String,Object> map=new HashMap<>();
        map.put("sname",state.getName());
        map.put("categoryId",state.getCountryId());
        KeyHolder keyHolder=new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
    }

    @Override
    public void delete(int id) {
     String sql="delete from states where id=:id";
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public State getCategoryById(State state) {
        String sql="select * from states where name=:name";
        List<State> list=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        map.put("name",state.getName());
       list= namedParameterJdbcTemplate.query(sql,map,new StateMapper());
       if(list.size()>0){
           return list.get(0);
       }
        return null;
    }

    @Override
    public void updateStateByCategoryId(State state) {
        String sql="update states set name=:name,country_id=:countryId where id=:id";
        Map<String,Object> map=new HashMap<>();
        map.put("name",state.getName());
        map.put("id",state.getId());
        map.put("countryId",state.getCountryId());
        namedParameterJdbcTemplate.update(sql,map);
    }
}
