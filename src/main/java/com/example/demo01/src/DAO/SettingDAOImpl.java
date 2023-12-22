package com.example.demo01.src.DAO;

import com.example.demo01.src.Mapper.SettingMapper;
import com.example.demo01.src.Pojo.Setting;
import com.example.demo01.src.Pojo.SettingCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SettingDAOImpl implements SettingDAO{

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Setting> listAllSettings() {
        String sql="select * from settings";
        Map<String,Object> map=new HashMap<>();
        List<Setting> list=new ArrayList<>();
        list=namedParameterJdbcTemplate.query(sql,map,new SettingMapper());
        if(list.size()>0){
            return list;
        }
        return null;
    }

    @Override
    public List<Setting> findByCategory(SettingCategory category) {
        String sql="select * from settings where category=:category";
        Map<String,Object> map=new HashMap<>();
        List<Setting> list=new ArrayList<>();
       map.put("category",category.name());
        list=namedParameterJdbcTemplate.query(sql,map,new SettingMapper());
        if(list.size()>0){
            return list;

        }
        return null;
    }

    @Override
    public void saveIterable(Iterable<Setting> settingList) {
        String sql = "UPDATE settings SET value = :value, category = :category WHERE `key` = :key";

        List<Map<String, Object>> batchValues = new ArrayList<>();
        for (Setting setting : settingList) {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("key", setting.getKey());
            paramMap.put("value", setting.getValue());
            paramMap.put("category", setting.getCategory().name());
            batchValues.add(paramMap);
        }

        namedParameterJdbcTemplate.batchUpdate(sql, batchValues.toArray(new Map[0]));
    }





    @Override
    public List<Setting> findByTwoCategory(SettingCategory category1, SettingCategory category2) {
        String sql="select * from settings where category=:category1 or category=:category2";
        Map<String,Object> map=new HashMap<>();
        List<Setting> list=new ArrayList<>();
        map.put("category1",category1.name());
        map.put("category2",category2.name());
        list=namedParameterJdbcTemplate.query(sql,map,new SettingMapper());
        if(list.size()>0){
            return  list;
        }
        return null;
    }

    @Override
    public Setting findByKey(String key) {
        String sql="select * from settings where `key`=:key";
        Map<String,Object> map=new HashMap<>();
        map.put("key",key);
        List<Setting> list=new ArrayList<>();
        list=namedParameterJdbcTemplate.query(sql,map,new SettingMapper());
        if(list.size()>0){
            return list.get(0);

        }
        return null;
    }
}
