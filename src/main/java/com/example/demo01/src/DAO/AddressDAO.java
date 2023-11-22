package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.Address;
import com.example.demo01.src.Pojo.Customer;

import java.util.List;

public interface AddressDAO {

    public List<Address> findByCustomer(Customer customer);
    public Address findByIdAndCustomer(Integer id,Customer customer);
    public void DeleteByIdAndCustomer(Integer id,Customer customer);
    public void CreateNewCustomer(Address address,Integer customerId);
    public Address ShowCustomerforedit(Integer addressid,Integer customerId);
    public void updateAddress(Address address);
    public void deleteAddress(Integer addressid,Integer customerId);
    public void setDefaultAddress(Integer addressidpublic,Integer customerId);
    public void setNonDefaultForOther(Integer addressid,Integer customerId);
    public  List<Address> findefaultAddressById(Integer customerid);
    public void setDefaultAddress4Primary(Integer addressid,Integer customerId);
}
