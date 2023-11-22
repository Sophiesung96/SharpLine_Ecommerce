package com.example.demo01.src.test.java.com.example.springboot_ecommerce.Service;

import com.example.springboot_ecommerce.DAO.ProductDAO;
import com.example.springboot_ecommerce.Pojo.Product;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductServiceImplTest {
    @Autowired
    ProductService productService;

    @MockBean
    ProductDAO productDAO;

    @Test
    public void getProductById(){
        List<Product> list=new ArrayList<>();
        list=productService.listAll();
        Mockito.when(productService.listAll()).thenReturn(list);

    }

}