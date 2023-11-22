package com.example.demo01.src.Service;

import com.example.springboot_ecommerce.Pojo.Address;
import com.example.springboot_ecommerce.Pojo.Customer;
import com.example.springboot_ecommerce.Pojo.ShippingRate;

public interface ShippingRateService {

    public ShippingRate getShippingRateForAddress(Customer customer, Address Defaultaddress);
    public ShippingRate getShippingRateforCustomer(Customer customer);


}
