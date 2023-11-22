package com.example.demo01.src.DAO;

import com.example.demo01.src.Mapper.CurrencyMapper;
import com.example.demo01.src.Pojo.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CurrencyDaoImpl implements CurrencyDao{

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Currency> findAllOrderByNameAsc() {
        String sql="select * from currencies order by name asc";
        List<Currency> list=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
       list= namedParameterJdbcTemplate.query(sql,map,new CurrencyMapper());
       if(list.size()>0){
           return list;
       }
        return null;
    }

    @Override
    public Optional<Currency> findById(int id) {
        String sql="select * from currencies where id=:id";
        List<Currency> list=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
       list= namedParameterJdbcTemplate.query(sql,map,new CurrencyMapper());
       if(list.size()>0){
           Currency currency=list.get(0);
           Optional<Currency> currency1=Optional.ofNullable(currency);
           return  currency1;
       }
        return Optional.empty();
    }
}
