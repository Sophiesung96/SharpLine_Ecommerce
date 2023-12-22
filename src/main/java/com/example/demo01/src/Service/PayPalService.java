package com.example.demo01.src.Service;

import com.example.demo01.src.Configuration.JWTUtil;
import com.example.demo01.src.Exception.PayPalApiException;
import com.example.demo01.src.Pojo.PayPalOrderResponse;
import com.example.demo01.src.Pojo.PaymentSettingBag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MultiValuedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
@Slf4j
public class PayPalService {


    @Autowired
    SettingService settingService;

    @Autowired
    JWTUtil jwtUtil;


    public boolean ValidateApi(String orderId){
        PayPalOrderResponse palOrderResponse = getPayPalOrdeDetails(orderId);
        return palOrderResponse.Validate(orderId);
    }


    private PayPalOrderResponse getPayPalOrdeDetails(String orderId) {
        PaymentSettingBag paymentSettingBag=settingService.getPaymentSetting();
        String requestURL="https://api.sandbox.paypal.com/v2/checkout/orders/"+ orderId;
        String clientId= paymentSettingBag.getClientID();
        String cleintSecret= paymentSettingBag.getClientSecret();
        HttpHeaders headers=new HttpHeaders();
        String jwtToken=jwtUtil.generateToken("Sanya Lad");
        jwtToken="Bearer "+jwtToken;
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Accept-Language","en_US");
        headers.setBearerAuth(jwtToken);
        headers.setBasicAuth(clientId,cleintSecret);
        HttpEntity<MultiValuedMap<String,String>> httpEntity=new HttpEntity<>(headers);
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<PayPalOrderResponse> response= restTemplate.exchange(requestURL, HttpMethod.GET,httpEntity,PayPalOrderResponse.class);
        HttpStatus statusCode=response.getStatusCode();
        if(!statusCode.equals(HttpStatus.OK)){
            throwExceptionForNonOK(statusCode);
        }
        PayPalOrderResponse palOrderResponse=response.getBody();
        log.info("ORDER ID:",palOrderResponse.getId());
        log.info("Validated:",palOrderResponse.Validate(orderId));

        return palOrderResponse;
    }



    private void throwExceptionForNonOK(HttpStatus statusCode) throws PayPalApiException {
        String message=null;
        switch(statusCode){
            case NOT_FOUND:{
                message=("Order ID Not Found");
            }
            case BAD_REQUEST:{
                message=("Bad Request to PayPal Checkout API");
            }
            case INTERNAL_SERVER_ERROR:{
                message=("PayPal Server Error");
            }
            default:
                message=("PayPal returned non-ok status");
        }
        throw new PayPalApiException(message);
    }
}
