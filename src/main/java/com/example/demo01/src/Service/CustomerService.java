package com.example.demo01.src.Service;

import com.example.springboot_ecommerce.Pojo.AuthenticationType;
import com.example.springboot_ecommerce.Pojo.Country;
import com.example.springboot_ecommerce.Pojo.Customer;
import java.util.List;

public interface CustomerService {


    public List<Customer> listAllCustomer();
    public List<Country> listAllCountries();
    public Customer findByEmail(String email);
    public Customer findByVerificationCode(String verificationCode);
    public boolean checkEmailUnique(String email);
    public void enabled(int id);
    public List<Customer> getCountryForCustomers();

    public void registerCustomer(Customer customer);

    public boolean verify(String verificationCode);

    void updateEnabledById(int enabled, int id);
    public Customer EditCustomerById(int id);
    public void updateCustomer(Customer customer);
    public void deleteCustomerById(int id);

    Customer findCustomerById(int id);
    Customer findCountryPerCustomerById(int id);

    public void updateAuthenticationType(Customer customer,AuthenticationType authenticationType);

    public Customer getCustomerByEmail(String email);
    public void addUponOAuth2Login(String email, String name,String countryCode);

    public Customer findByCustomerName(String name);

    public String updateResetPasswordToken(String email);
    public Customer GetByResetPasswordToken(String token);

    public void updateCustomerNewPasswordByToken(String token,String newPassword);

    public Customer getCustomerByfullName(String fullName);





}
