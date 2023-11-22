package com.example.demo01.src.Mapper;

import com.example.springboot_ecommerce.Pojo.Setting;
import com.example.springboot_ecommerce.Pojo.SettingCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingMapper implements RowMapper<Setting> {

    @Override
    public Setting mapRow(ResultSet resultSet, int i) throws SQLException {
        Setting setting=new Setting();
        setting.setCategory(SettingCategory.valueOf(resultSet.getString("category")));
        setting.setKey(resultSet.getString("key"));
        setting.setValue(resultSet.getString("value"));
        return setting;
    }
}
