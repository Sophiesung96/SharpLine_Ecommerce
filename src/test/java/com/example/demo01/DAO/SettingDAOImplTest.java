package com.example.demo01.src.test.java.com.example.springboot_ecommerce.DAO;

import com.example.springboot_ecommerce.Pojo.Setting;
import com.example.springboot_ecommerce.Pojo.SettingCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
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