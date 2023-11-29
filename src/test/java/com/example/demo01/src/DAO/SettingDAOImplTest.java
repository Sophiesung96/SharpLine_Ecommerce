package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.Setting;
import com.example.demo01.src.Pojo.SettingCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest
class SettingDAOImplTest {

    @Autowired
    SettingDAO settingDAO;

    @Test
    public void findByTwoCategory(){
        List<Setting> list=new ArrayList<>();
        SettingCategory c1=SettingCategory.CURRENCY;
        SettingCategory c2=SettingCategory.GENERAL;
        list=settingDAO.findByTwoCategory(c1,c2);
        assertNotNull(list);
        list.forEach(s-> System.out.println(s.getKey()));



    }
}