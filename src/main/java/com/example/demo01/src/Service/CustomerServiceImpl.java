package com.example.demo01.src.Service;

import com.example.springboot_ecommerce.DAO.CountryDao;
import com.example.springboot_ecommerce.DAO.CustomerDao;
import com.example.demo01.src.Exception.CustomerNotFoundException;
import com.example.springboot_ecommerce.Pojo.AuthenticationType;
import com.example.springboot_ecommerce.Pojo.Country;
import com.example.springboot_ecommerce.Pojo.Customer;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    CustomerDao customerDao;

    @Autowired
    CountryDao countryDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<Country> listAllCountries() {
        List<Country> list=new ArrayList<>();
        list=countryDao.findAllByOrderByNameAsc();
        return list;
    }

    @Override
    public Customer findByEmail(String email) {
        Customer customer= customerDao.findByEmail(email);
        return customer;
    }

    @Override
    public Customer findByVerificationCode(String verificationCode) {
        Customer customer= customerDao.findByVerificationCode(verificationCode);
        return customer;
    }

    @Override
    public void enabled( int id) {
           customerDao.enabled(id);
    }

    @Override
    public void registerCustomer(Customer customer) {
        encodePassword(customer);
        customer.setEnabled(0);
        customer.setCreatedTime(new Date());
        String ramdomCode=RandomString.make(64);
        customer.setVerificationCode(ramdomCode);
        customer.setAuthenticationType("DATABASE");
        customerDao.registerCustomer(customer);
    }

    private void encodePassword(Customer customer) {

            String original=customer.getPassword();
            String newpassword=passwordEncoder.encode(original);
            customer.setPassword(newpassword);
    }


    @Override
    public boolean verify(String verificationCode) {
        Customer customer = customerDao.findByVerificationCode(verificationCode);
        if (customer == null | customer.getEnabled() == 1) {
            return false;
        } else {
            int id = customer.getId();
            customerDao.enabled(id);
            return true;
        }
    }

    @Override
    public void updateEnabledById(int enabled, int id) {
           customerDao.updateEnabledById(enabled,id);
    }


    @Override
    public boolean checkEmailUnique(String email) {
        List<Customer> list= customerDao.checkEmailUnique(email);
       return list==null;
    }

    @Override
    public List<Customer> listAllCustomer() {
        List<Customer> list=customerDao.listAllCustomer();
        return list;
    }

    @Override
    public List<Customer> getCountryForCustomers() {
        List<Customer> list=customerDao.getCategoryForCustomers();
        return list;
    }

    @Override
    public Customer EditCustomerById(int id) {
        Customer customer=customerDao.EditCustomerById(id);
        return customer;
    }

    @Override
    public void updateCustomer(Customer customer) {
        Customer oldcustomer=customerDao.findCustomerById(customer.getId());
        // customer is from database authentication
        if(oldcustomer.getAuthenticationType().equals("DATABASE")){
            if(!customer.getPassword().isEmpty()){
                String original=customer.getPassword();
                String newpassword=passwordEncoder.encode(original);
                customer.setPassword(newpassword);
            }
            else{
                customer.setPassword(oldcustomer.getPassword());
            }
        }
        // customer is from google oauth2
        else{
            customer.setPassword(oldcustomer.getPassword());
        }

        //customers don't want to change their original passwords
        // or customer is authenticated by google oauth2
        customer.setEnabled(1);
        customer.setCreatedTime(oldcustomer.getCreatedTime());
        customer.setVerificationCode(oldcustomer.getVerificationCode());
        customer.setAuthenticationType(customer.getAuthenticationType());
        customer.setResetPasswordToken(oldcustomer.getResetPasswordToken());
      customerDao.updateCustomer(customer);
    }

    @Override
    public void deleteCustomerById(int id) {
        customerDao.deleteCustomerById(id);
    }

    @Override
    public Customer findCustomerById(int id) {

        Customer customer= customerDao.findCustomerById(id);
        return customer;
    }

    @Override
    public Customer findCountryPerCustomerById(int id) {
        Customer customer=customerDao.findCountryPerCustomerById(id);
        return customer;
    }

    @Override
    public void updateAuthenticationType(Customer customer,AuthenticationType authenticationType) {
        if(!customer.getAuthenticationType().equals(authenticationType.name()));
        customerDao.updateAuthenticationType(customer.getId(), authenticationType);
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        Customer customer=  customerDao.getCustomerByEmail(email);
        return customer;
    }
    @Override
    public void addUponOAuth2Login(String email, String name,String countryCode) {
        Customer customer=new Customer();
        customer.setEmail(email);
        setCustomerNameByGoogle(customer,name);
        customer.setCreatedTime(new Date());
        customer.setAuthenticationType("GOOGLE");
        customer.setPassword("");
        customer.setEnabled(1);
        customer.setState("");
        customer.setCity("");
        customer.setCountryId(countryDao.findByCountryCode(countryCode).getId());
        customer.setPostalCode("");
        customer.setAddressline1("");
        customer.setAddressline2("");
        customer.setPhoneNumber("");
        customerDao.registerCustomer(customer);
    }

    private void setCustomerNameByGoogle(Customer customer,String name){
        String []namearray=name.split(" ");
        if(namearray.length<2){
            customer.setFirstName(name);
            customer.setLastName("");
        }
        else{
            customer.setFirstName(namearray[0]);
            customer.setLastName(namearray[1]);
        }
    }




    @Override
    public Customer findByCustomerName(String name) {
        Customer customer=customerDao.findByName(name);
        return customer;
    }

    @Override
    public String  updateResetPasswordToken(String email) {
        Customer customer=customerDao.getCustomerByEmail(email);
        if(customer!=null){
            String resetToken=RandomString.make(30);
            customer.setResetPasswordToken(resetToken);
            customerDao.updateResetPasswordToken(email,resetToken);
            return resetToken;
        }
        else{
            throw new CustomerNotFoundException("Could not find any user with this email!"+email);
        }

    }

    @Override
    public Customer GetByResetPasswordToken(String token) {
         Customer customer=customerDao.findByResetPasswordToken(token);
         return customer;
    }

    @Override
    public void updateCustomerNewPasswordByToken(String token, String newPassword) {
        Customer customer=GetByResetPasswordToken(token);
        if(customer==null){

            throw new CustomerNotFoundException("No Customer Found: Invalid Token");

        }
        customer.setPassword(newPassword);
        //clean out the original reset password token
        customer.setResetPasswordToken(null);
        encodePassword(customer);
        customerDao.updateCustomerNewPasswordByToken(token,customer.getPassword());
    }

    @Override
    public Customer getCustomerByfullName(String fullName) {
        Customer customer=customerDao.getCustomerByfullName(fullName);
        return customer;
    }
}

