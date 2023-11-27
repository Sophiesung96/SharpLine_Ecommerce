package com.example.demo01.src.Service;

import com.example.demo01.src.Pojo.Address;
import com.example.demo01.src.Pojo.Customer;
import com.example.demo01.src.Pojo.ShippingRate;

public interface ShippingRateService {

    public ShippingRate getShippingRateForAddress(Customer customer, Address Defaultaddress);
    public ShippingRate getShippingRateforCustomer(Customer customer);


}
