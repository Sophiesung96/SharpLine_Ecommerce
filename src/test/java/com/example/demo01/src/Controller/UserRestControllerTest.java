package com.example.demo01.src.Controller;

import com.example.demo01.src.Pojo.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class UserRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void getById() throws Exception {
        Users user=new Users();
        user.setEmail("haha@gmail.com");
        String data=objectMapper.writeValueAsString(user);
        RequestBuilder requestBuilder= MockMvcRequestBuilders
                .post("/checkEmail")
                .contentType(MediaType.APPLICATION_JSON)
                .content(data);
      mockMvc.perform(requestBuilder)
                .andDo(print());





    }


}