package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.Brand;
import com.example.demo01.src.Pojo.BrandCategoryName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BrandDAOImplTest {

    @SpyBean
    BrandDAO brandDAO;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void TestgetCategoryById() throws JsonProcessingException {
        int brandId = 2;
        List<BrandCategoryName> list = new ArrayList<>();
        list = brandDAO.getCategoryById(brandId);
        list.stream().forEach(brandCategoryName -> assertEquals("Electronics",brandCategoryName.getName()));
    }

    @Test
    public void testGetBrandCount(){
        int number=5;
        Mockito.when(brandDAO.getBrandCount()).thenReturn(number);

    }
    @Test
    public void testGetFilterByKeyword(){
        String keyword="toshiba";
        List<Brand>list= brandDAO.getFilterByKeyword(keyword);
        list.stream().forEach(brand -> assertEquals("Toshiba",brand.getName()));
    }



}