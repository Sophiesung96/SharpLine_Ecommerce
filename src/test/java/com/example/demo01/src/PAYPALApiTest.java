package com.example.demo01.src;

import com.example.demo01.src.Configuration.JWTUtil;
import com.example.demo01.src.Pojo.PayPalOrderResponse;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootTest
public class PAYPALApiTest {

        private static final String BASE_URL="https://api.sandbox.paypal.com";
        private static final String GET_ORDER_API="/v2/checkout/orders/";
        private static final String CLIENT_ID="AUVk2RKhRzO5cVMo9svMSWwOUC0GQflWrbcxXbQzvofpIaKwB1HSkDbdivN9H6RYtWNdSNTLw2xdi8fh";
        private static final String CLIENT_SECRET="EH9pCpXmt5MjcKQ7QkfW54bcXxQzRd8x9eiK4RivPdgv8keQ7YwPVNggtBblT_Ms4ZzvtHWfl7H3wkdV";

    @Autowired
    JWTUtil jwtUtil;

        @Test
        public void testApi(){
            String orderId="06H74251F5331660W";
            String requestURL=BASE_URL+GET_ORDER_API+orderId;
            HttpHeaders headers=new HttpHeaders();
            String jwtToken=jwtUtil.generateToken("Sanya Lad");
            jwtToken="Bearer "+jwtToken;
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("Accept-Language","en_US");
             headers.setBearerAuth(jwtToken);
            headers.setBasicAuth(CLIENT_ID,CLIENT_SECRET);
            HttpEntity<MultiValuedMap<String,String>> httpEntity=new HttpEntity<>(headers);
            RestTemplate restTemplate=new RestTemplate();
         ResponseEntity<PayPalOrderResponse> response= restTemplate.exchange(requestURL, HttpMethod.GET,httpEntity,PayPalOrderResponse.class);
            System.out.println("ORDER ID:"+ response.getBody().getId());
            System.out.println("Validated:"+ response.getBody().Validate(orderId));
        }




}
