package com.example.demo01.src.test.java.com.example.springboot_ecommerce.Controller;

import com.example.springboot_ecommerce.Pojo.Country;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CountryRestControllerTest  {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @WithMockUser(username = "nam@codejava.net",password = "{noop}ha123",roles="Admin")
    public void testListCountries() throws Exception {
        String url="/countries/list";
        MvcResult mvcResult=mockMvc.perform(get(url))
                .andExpect(status().is(200))
                .andDo(print())
                .andReturn();
        String jsonResponse=mvcResult.getResponse().getContentAsString();
        System.out.println(jsonResponse);
        Country[] c=objectMapper.readValue(jsonResponse, Country[].class);
       assertNotNull(c);

    }


    @Test
    @WithMockUser(username = "nam@codejava.net",password = "{noop}ha123",roles="Admin")
    public void testCreateCountries() throws Exception {
        String url="/countries/save";
        String countryName="Canada";
        String countryCode="CA";
        Country country=new Country(countryName,countryCode);
        mockMvc.perform(post(url).contentType("application/json")
                .content(objectMapper.writeValueAsString(country))
                                .with(csrf())
                ).andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    @WithMockUser(username = "nam@codejava.net",password = "{noop}ha123",roles="Admin")
    public void testUpdateCountries() throws Exception {
        String url="/countries/save";
        String countryName="Japan";
        //Integer countryid=7;
        String countryCode="JP";
        Country country=new Country(countryName,countryCode);
        mockMvc.perform(post(url).contentType("application/json")
                        .content(objectMapper.writeValueAsString(country))
                        .with(csrf())
                ).andDo(print())
                .andExpect(status().isOk());

    }


    @Test
    @Transactional
    @WithMockUser(username = "nam@codejava.net",password = "{noop}ha123",roles="Admin")
    public void testDelete() throws Exception {
        Integer countryId=7;
        String url="/countries/delete/"+countryId;

        mockMvc.perform(get(url))
                        .andExpect(status().isOk())
                        .andDo(print());
    }


    @Test
    @WithMockUser(username = "nam@codejava.net", password = "{noop}ha123", roles = "Admin")
    public void testRegisterCountry() throws Exception {
        String url = "/register/listcountry/2";
        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(jsonPath("$..name", hasItem("Wisconsin")))
                .andExpect(jsonPath("$..name",hasItem("Alaska")));
    }





}