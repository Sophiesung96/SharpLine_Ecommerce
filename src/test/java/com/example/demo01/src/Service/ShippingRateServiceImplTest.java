package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.ShippingRateDAO;
import com.example.demo01.src.Exception.ShippingRateNotFoundException;
import com.example.demo01.src.Pojo.ShippingRate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ShippingRateServiceImplTest {
    @SpyBean
    ShippingRateDAO shippingRateDAO;
    @Autowired
    ShippingRateService shippingRateService;

    @Test
    public void testCalculateShippingCost() {
        int productId=1;
        String state="Victoria";
        int countryId=14;
       ShippingRate shippingRate= shippingRateDAO.getShippingRateForAddress(state,countryId);
      float cost= shippingRateService.calculateShippingCost(productId,countryId,state);
        System.out.println(cost);

    }
    @Test
    public void testWrongCalculateShippingCost(){
        int productId=1;
        String state="ABCDE";
        int countryId=5;
        ShippingRate shippingRate= shippingRateDAO.getShippingRateForAddress(state,countryId);
        assertThrows(ShippingRateNotFoundException.class, () -> {
         shippingRateService.calculateShippingCost(productId,countryId,state);
        });
    }

}