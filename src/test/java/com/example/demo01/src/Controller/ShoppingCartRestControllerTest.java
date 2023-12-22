package com.example.demo01.src.Controller;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ShoppingCartRestControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void testAddtoCart() throws Exception {
        String url="/cart/add/1/2";

        mockMvc.perform(post(url))
                .andDo(print())
                .andExpect(status().is(302));
    }

}