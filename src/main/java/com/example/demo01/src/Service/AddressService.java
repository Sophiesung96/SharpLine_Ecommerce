package com.example.demo01.src.Service;

import com.example.demo01.src.Pojo.Address;
import com.example.demo01.src.Pojo.Customer;

import java.util.List;

public interface AddressService {
    public List<Address> findByCustomer(Customer customer);
    public Address findByIdAndCustomer(Integer id,Customer customer);
    public void CreateNewCustomer(Address address,int customerId);
    public Address ShowCustomerforedit(int addressid,int customerId);
    public void updateAddress(Address address);
    public void deleteAddress(int addressid,int customerId);
    public void setDefaultAddress(int addressid,int customerId);
    public Address findefaultAddressById(Integer customerid);
}

