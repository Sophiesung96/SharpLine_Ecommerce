package com.example.demo01.src.DAO;

import com.example.demo01.src.Mapper.CountryMapper;
import com.example.demo01.src.Pojo.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CountryDaoImpl implements CountryDao{
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Country> findAllByOrderByNameAsc() {
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
    public void saveCountry(Country country) {
        List<Country> list=new ArrayList<>();
        String sql="insert into countries(id,name,code) values(:id,:name,:code)";
        Map<String,Object> map=new HashMap<>();
        map.put("id",country.getId());
        map.put("name",country.getName());
        map.put("code",country.getCode());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public Country getByCountryId(int countryId) {
        List<Country> list=new ArrayList<>();
        String sql="select * from countries where id=:id";
        Map<String,Object> map=new HashMap<>();
        map.put("id",countryId);
        list=namedParameterJdbcTemplate.query(sql,map,new CountryMapper());
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public void DeleteById(Integer id) {
        String sql="delete from countries where id=:id";
        Map<String,Object>map=new HashMap<>();
        map.put("id",id);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void updateCountry(Country country) {
        String sql="update countries set name=:name, code=:code where id=:id";
        Map<String,Object>map=new HashMap<>();
        map.put("id",country.getId());
        map.put("name",country.getName());
        map.put("code",country.getCode());
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public Country findByCountryCode(String countryCode) {
        String sql="select * from countries where code=:code";
        Map<String,Object>map=new HashMap<>();
        map.put("code",countryCode);
        List<Country> list=new ArrayList<>();
        list=namedParameterJdbcTemplate.query(sql,map,new CountryMapper());
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }
}



