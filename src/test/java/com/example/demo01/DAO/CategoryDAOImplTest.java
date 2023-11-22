package com.example.demo01.src.test.java.com.example.springboot_ecommerce.DAO;

import com.example.springboot_ecommerce.Pojo.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CategoryDAOImplTest {

    @Autowired
    CategoryDAO categoryDAO;

    @Test
    public void findByAliasEnabled(){
        String alias="Computers";
        Category category=categoryDAO.findByAliasEnabled(alias);

       assertNotNull(category);
       assertEquals(category.getNickname(),alias);
       assertEquals(category.getName(),"Computers");
       assertEquals(category.getEnabled(),"true");
        assertEquals(category.getId(),1);
    }
    @Test
    public void findcategoryByName(){
        String name="Laptops";
        Category category=categoryDAO.findByName(name);
        assertEquals(name,category.getName());

    }

}