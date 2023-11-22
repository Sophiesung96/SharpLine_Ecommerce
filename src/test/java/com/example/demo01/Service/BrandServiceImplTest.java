package com.example.demo01.src.test.java.com.example.springboot_ecommerce.Service;

import com.example.springboot_ecommerce.DAO.BrandDAO;
import com.example.springboot_ecommerce.Pojo.Brand;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BrandServiceImplTest {

    @Autowired
    BrandService brandService;

    @MockBean
    BrandDAO brandDAO;

    @Test
    public void getid() {
        Brand b = new Brand();
        b.setName("Samsung Electronics");
        b.setId(2);
        Mockito.when(brandDAO.selectBrandById(2)).thenReturn(b);
        Brand brand = brandService.selectBrandById(2);
        assertNotNull(brand);
        assertEquals(2, brand.getId());
        assertEquals("Samsung Electronics", brand.getName());
    }




}