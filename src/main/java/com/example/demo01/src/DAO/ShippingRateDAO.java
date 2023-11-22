package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.ShippingRate;

public interface ShippingRateDAO {
    public ShippingRate getShippingRateForAddress(String state, int  CountryId);

}
