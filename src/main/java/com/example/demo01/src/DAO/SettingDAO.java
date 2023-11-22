package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.Setting;
import com.example.demo01.src.Pojo.SettingCategory;

import java.util.List;

public interface SettingDAO {

    public List<Setting> listAllSettings();

    public List<Setting> findByCategory(SettingCategory category);
    public List<Setting> findByTwoCategory(SettingCategory category1,SettingCategory category2 );
    public void saveIterable(Iterable<Setting> iterable);
}
