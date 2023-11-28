package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.AuthenticationType;
import com.example.demo01.src.Pojo.Customer;

import java.util.List;

public interface CustomerDao {

    public List<Customer> listAllCustomer();
    public Customer findByEmail(String email);
    public Customer findByName(String name);
     Customer findByVerificationCode(String verificationCode);
    public List<Customer> getCategoryForCustomers();

    public List<Customer> checkEmailUnique(String email);

    public void enabled(int id);

    public void registerCustomer(Customer customer);

    public void updateEnabledById( int enabled,int id);
    public Customer EditCustomerById(int id);

    public void updateCustomer(Customer customer);

    public void deleteCustomerById(int id);
    Customer findCustomerById(int id);

    Customer findCountryPerCustomerById(int id);

    public void updateAuthenticationType(int id,AuthenticationType authenticationType);
    public Customer getCustomerByEmail(String email);

    public void updateResetPasswordToken(String email,String resetToken);

    Customer findByResetPasswordToken(String resetPasswordToken);

    public void updateCustomerNewPasswordByToken(String token,String newPassword);

    public Customer getCustomerByfullName(String fullName);
    public void encodePasswordByCustomerId(int customerId,String encodedPassword);



}
