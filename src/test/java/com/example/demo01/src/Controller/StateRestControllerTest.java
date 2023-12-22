package com.example.demo01.src.Controller;

import com.example.demo01.src.DAO.StateDao;
import com.example.demo01.src.Pojo.Country;
import com.example.demo01.src.Pojo.State;
import com.example.demo01.src.Service.CountryService;
import com.example.demo01.src.Service.StateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StateRestControllerTest {

    @Autowired
    StateService stateService;

    @Autowired
    CountryService countryService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    StateDao stateDao;

    @Test
    @WithMockUser(username = "nam@codejava.net",password = "{noop}ha123",roles="Admin")
    public void testListBYCountries() throws Exception {
        int countryId=2;
        String url="/states/list_by_country/"+countryId;
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andDo(print());

    }
    @Test
    @Transactional
    @WithMockUser(username = "nam@codejava.net",password = "{noop}ha123",roles="Admin")
    public void testSave() throws Exception  {
        String url="/states/save";
        String countryName="Australia";
        Country country=new Country(countryName);
       Country ccountry=countryService.getByCountryId(country.getId());
       State state=new State();
       state.setName("Tavush");
       state.setCountryId(ccountry.getId());
        mockMvc.perform(post(url).contentType("application/json")
                        .content(objectMapper.writeValueAsString(state))
                        .with(csrf())
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @WithMockUser(username = "nam@codejava.net",password = "{noop}ha123",roles="Admin")
    public void testDelete() throws Exception {
        Integer countryId=7;
        String url="/states/delete/"+countryId;

        mockMvc.perform(get(url))
                .andDo(print());
    }


    @Test
    @Transactional
    @WithMockUser(username = "nam@codejava.net",password = "{noop}ha123",roles="Admin")
    public void testUpdate() throws Exception {
        Integer countryId=7;
        String url="/states/update/";
        State state=new State("test",1);
        state.setId(1);

        mockMvc.perform(post(url).contentType("application/json").content(objectMapper.writeValueAsString(state)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void test() throws Exception {

    }






}