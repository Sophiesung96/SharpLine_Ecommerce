package com.example.demo01.src.Service;

import com.example.springboot_ecommerce.DAO.CustomerDao;
import com.example.springboot_ecommerce.DAO.ShippingRateDAO;
import com.example.springboot_ecommerce.Pojo.Address;
import com.example.springboot_ecommerce.Pojo.Customer;
import com.example.springboot_ecommerce.Pojo.ShippingRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShippingRateServiceImpl implements  ShippingRateService{
    @Autowired
    ShippingRateDAO shippingRateDAO;

    @Autowired
    CustomerDao customerDao;

    @Override
    public ShippingRate getShippingRateForAddress(Customer customer, Address Defaultaddress) {
        ShippingRate shippingRate=new ShippingRate();
         if(Defaultaddress!=null){
             //address is set to primary address
             // so this address cannot be found in address table
                shippingRate=shippingRateDAO.getShippingRateForAddress(Defaultaddress.getState(), Defaultaddress.getCountryId());

         }else{
              shippingRate=getShippingRateforCustomer(customer);
             return shippingRate;
         }
        return shippingRate;
    }

    @Override
    public ShippingRate getShippingRateforCustomer(Customer customer) {
        Customer customer1=customerDao.findCustomerById(customer.getId());
        ShippingRate shippingRate=new ShippingRate();
        if(customer1!=null){
             shippingRate=shippingRateDAO.getShippingRateForAddress(customer1.getState() ,customer1.getCountryId());

        }
        return shippingRate;
    }
}
