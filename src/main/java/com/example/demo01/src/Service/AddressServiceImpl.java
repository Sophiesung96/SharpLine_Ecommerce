package com.example.demo01.src.Service;

import com.example.springboot_ecommerce.DAO.AddressDAO;
import com.example.springboot_ecommerce.DAO.CustomerDao;
import com.example.springboot_ecommerce.Pojo.Address;
import com.example.springboot_ecommerce.Pojo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{
    @Autowired
    AddressDAO addressDAO;

    @Autowired
    CustomerDao customerDao;

    @Override
    public List<Address> findByCustomer(Customer customer) {
        List<Address> list=new ArrayList<>();
         list=addressDAO.findByCustomer(customer);
        return list;
    }

    @Override
    public Address findByIdAndCustomer(Integer id, Customer customer) {
        return null;
    }

    @Override
    public void CreateNewCustomer(Address address, int customerId) {
        addressDAO.CreateNewCustomer(address,customerId);
    }

    @Override
    public Address ShowCustomerforedit(int addressid, int customerId) {
        Address address=addressDAO.ShowCustomerforedit(addressid,customerId);
        return address;
    }

    @Override
    public void updateAddress(Address address) {
        addressDAO.updateAddress(address);
    }

    @Override
    public void deleteAddress(int addressid, int customerId) {
        addressDAO.deleteAddress(addressid,customerId);
    }


    @Override
    public int setDefaultAddress(int addressid, int customerId) {

        int primary=0;
        if(addressid>1){
            List<Address> addressList= addressDAO.findefaultAddressById(customerId);
            //default address is switched from addresses
            //that is not primary
            if(addressList!=null){
               for(Address newDefault:addressList){
                   //retrieve the previous default and make it not
                   addressDAO.setNonDefaultForOther(newDefault.getId(),customerId);
                   //set the new address as default
                   addressDAO.setDefaultAddress(addressid,customerId);
               }
               }

            //make the new address as default
            //but the previous one is primary
            else{
                addressDAO.setDefaultAddress(addressid,customerId);
            }

            return primary;
        //setting primary as a default address
        }else{
            primary=1;
           addressDAO.setDefaultAddress4Primary(addressid,customerId);
            return primary;

        }    }


    @Override
    public Address findefaultAddressById(Integer customerid) {
        Address address=new Address();
        return address;
    }



}
