package com.example.demo01.src.DAO;

import com.example.demo01.src.Mapper.ShippingRateMapper;
import com.example.demo01.src.Pojo.ShippingRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ShippingRateDAOImpl implements ShippingRateDAO{
 @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public ShippingRate getShippingRateForAddress(String state, int  CountryId) {
        String sql="select * from ShippingRate where country_id=:countryid and state=:state ";
        Map<String,Object> map=new HashMap<>();
        map.put("countryid",CountryId);
        map.put("state",state);
       List<ShippingRate> list= namedParameterJdbcTemplate.query(sql,map,new ShippingRateMapper());
       if(list.size()>0){
           return list.get(0);

       }        return null;
    }


}
