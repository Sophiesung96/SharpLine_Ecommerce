package com.example.demo01.src.Controller;

import com.example.demo01.src.Service.OrderService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderRestControllerTest {
    @Autowired
    OrderService orderService;

    @Autowired
    MockMvc mockMvc;


    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    @Test
    public void setup()
    {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testUpdateStatus() throws Exception {
        int id=1;
        String status="NEW";
        String url="/orders_shipper/update/"+id+"/"+status;
        mockMvc.perform(post(url).contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());

    }


}