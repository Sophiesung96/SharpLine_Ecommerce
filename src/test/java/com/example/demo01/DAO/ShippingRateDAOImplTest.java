package com.example.demo01.src.test.java.com.example.springboot_ecommerce.DAO;

import com.example.springboot_ecommerce.Pojo.Customer;
import com.example.springboot_ecommerce.Pojo.ShippingRate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ShippingRateDAOImplTest {

    @Autowired
    @SpyBean
    ShippingRateDAO shippingRateDAO;

    @Autowired
    @SpyBean
    CustomerDao customerDao;

    @Test
    public void testGetShippingRateForAddress(){
        Customer customer1=customerDao.findCustomerById(2);
        ShippingRate shippingRate=new ShippingRate();
        if(customer1!=null){
            shippingRate=shippingRateDAO.getShippingRateForAddress(customer1.getState() ,customer1.getCountryId());

        }
        System.out.println(shippingRate);
    }
}