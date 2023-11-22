package com.example.demo01.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class ShoppingCartRestControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void testAddtoCart() throws Exception {
        String url="/cart/add/1/2";

        mockMvc.perform(post(url))
                .andDo(print());
    }

}