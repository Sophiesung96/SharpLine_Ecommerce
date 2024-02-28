package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.ProductDAO;
import com.example.demo01.src.Pojo.Product;
import com.example.demo01.src.Service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ProductServiceImplTest {
    @Autowired
    ProductService productService;

   @SpyBean
    ProductDAO productDAO;

    @Test
    public void testGetProductById(){
        List<Product> list=new ArrayList<>();
        list=productService.listAll();
        Mockito.when(productService.listAll()).thenReturn(list);

    }

    @Test
    public void testGetProductByNickName(){
      Product product=productService.findByNickName("samsung-Galaxy-A31");
        System.out.println(product);
    }

}