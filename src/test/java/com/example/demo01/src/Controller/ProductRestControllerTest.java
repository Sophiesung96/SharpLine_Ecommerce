package com.example.demo01.src.Controller;

import com.example.demo01.src.Pojo.Product;
import com.example.demo01.src.Service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc()
class ProductRestControllerTest {

    @Autowired
    ProductService productService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper objectMapper;


    @BeforeEach
    @Test
    public void setup()
    {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetProductInfo() throws Exception {
        String url="/products/get/1";
          mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(jsonPath("$.id",equalTo(1)))
                .andExpect(jsonPath("$.name",equalTo("Samsung Galaxy A31")))
                .andExpect(jsonPath("$.alias",equalTo("samsung-Galaxy-A31")));
    }
    @Test
    public void testProductNameUniqueness() throws Exception {
        String url="/productunique";
        Product product=new Product();
        product.setId(1);
        product.setName("Samsung Galaxy A31");
         mockMvc.perform(post(url).content(objectMapper.writeValueAsString(product))
                 .contentType("application/json")
                 .with(csrf()))
                 .andDo(print());
    }




}