package com.example.demo01.src.Controller;

import com.example.demo01.src.DAO.CustomerDao;
import com.example.demo01.src.Pojo.Customer;
import com.example.demo01.src.Pojo.OrderReturnRequest;
import com.example.demo01.src.Security.CustomUserDetail;
import com.example.demo01.src.Security.CustomUserDetailsService;
import com.example.demo01.src.Service.CustomerService;
import com.example.demo01.src.Service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
    ObjectMapper objectMapper;
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    CustomerService customerService;


    @Test
  public void testUpdateStatus() throws Exception {
        int id=1;
        String status="NEW";
        String url="/orders_shipper/update/"+id+"/"+status;
        mockMvc.perform(post(url).contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());

    }


    @Test
    @WithUserDetails(value = "Sanya", userDetailsServiceBeanName = "customUserDetailsService")
    public void testOrderReturnRequestWithAuthenticatedUserFailed() throws Exception {
        Integer orderId = 1111;
        OrderReturnRequest request = new OrderReturnRequest(orderId, "", "");
        String requestURL = "/customers/Order/return";



        mockMvc.perform(post(requestURL).contentType("application/json")
                        .content(objectMapper.writeValueAsString(request))
                ).andDo(print())
                .andExpect(status().isNotFound())
               ;
    }

    @Test
    @WithUserDetails(value = "Sanya", userDetailsServiceBeanName = "customUserDetailsService")
    public void testOrderReturnRequestWithAuthenticatedUserSuccessful() throws Exception {
        Integer orderId = 24;
        String reason="I bought the wrong items";
        String notes="Please return my money.";
        OrderReturnRequest request = new OrderReturnRequest(orderId, reason, notes);
        String requestURL = "/customers/Order/return";

        mockMvc.perform(post(requestURL).contentType("application/json")
                        .content(objectMapper.writeValueAsString(request))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",equalTo(24)));

    }




}