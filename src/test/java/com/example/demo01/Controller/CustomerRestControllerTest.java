package com.example.demo01.src.test.java.com.example.springboot_ecommerce.Controller;

import com.example.springboot_ecommerce.Pojo.Customer;
import com.example.springboot_ecommerce.Service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CustomerService customerService;

    @Test
    @WithMockUser(username = "nam@codejava.net", password = "{noop}ha123", roles = "Admin")
    public void getEmailUnique() throws Exception {
        String url = "/register/check_UniqueEmail";
        Customer customer = customerService.findCustomerById(1);
        boolean unique = customerService.checkEmailUnique(customer.getEmail());
        mockMvc.perform(post(url)
                        .content(objectMapper.writeValueAsString(customer))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "nam@codejava.net", password = "{noop}ha123", roles = "Admin")
    public void listCountry4Registration() throws Exception {
        String url = "/register/listcountry/1";
        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(jsonPath("$..name", hasItem("Andhra Pradesh")))
                .andExpect(jsonPath("$..name",hasItem("Arunachal Pradesh")))
                .andExpect(jsonPath("$..countryId",hasItem(1)));
    }


}