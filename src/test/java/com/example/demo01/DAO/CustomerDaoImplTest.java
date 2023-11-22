package com.example.demo01.src.test.java.com.example.springboot_ecommerce.DAO;

import com.example.springboot_ecommerce.Pojo.AuthenticationType;
import com.example.springboot_ecommerce.Pojo.Customer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CustomerDaoImplTest {
  @SpyBean
    CustomerDao customerDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void getCustomerByEmail(){

        String email="davidfountain@gmail.com";
        String email1="sanyadru@yahoo.com.us";
        Customer customer=customerDao.findByEmail(email);
        Customer customertest2=customerDao.findByEmail(email1);
        assertEquals(customertest2.getEmail(),email1);
        assertNotNull(customer);
        assertNotNull(customertest2);
    }

    @Test
    public void getCustomerByVerificationCode(){

    }
    @Test
    public void testenabled(){
      customerDao.enabled(25);

    }
    @Test
    public void testGetAllCountriesForCustomer(){
        List<Customer>list= customerDao.getCategoryForCustomers();
        list.forEach(country-> assertNotNull(country));

    }

    @Test
    @Transactional
    public void testdeleteCustomerById(){
        int id=1;
        customerDao.deleteCustomerById(1);
    }

    @Test
    public void testfindCustomerById(){
       Customer customer= customerDao.findCustomerById(1);
       assertNotNull(customer);
       assertEquals(1,customer.getId());
       assertEquals("davidfountain@gmail.com",customer.getEmail());
       assertEquals("David",customer.getFirstName());
       assertEquals("Fountaine",customer.getLastName());
       assertEquals("321-446-897",customer.getPhoneNumber());
       assertEquals("2937 West Drive",customer.getAddressline1());
       assertEquals("Sacramento",customer.getCity());
       assertEquals("California",customer.getState());
       assertEquals("95867",customer.getPostalCode());



    }
    @Test
    public void testCustomerPassword(){
        String rawPassword = "sanyatesting";
        String encodedPassword = "$2a$10$B1WC0ZXWutMIfCgeRcR.NOl0IgFvlPvQxQ5M2ash5zdqIazY8zOOe";

        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        System.out.println("Password matches: " + matches);



    }
    @Test
    public void testUpdateAuthenticationType(){
        int id=1;
        Customer customer=new Customer();
        customer.setAuthenticationType("FACEBOOK");
        Mockito.when(customerDao.findCustomerById(1)).thenReturn(customer);
       assertEquals(customer.getAuthenticationType(),AuthenticationType.FACEBOOK.name());
    }

    @Test
    public void testGetCustomerByEmail(){
        String email="tu6010534@gmail.com";
        Customer customer= customerDao.getCustomerByEmail(email);
        assertEquals(customer.getEmail(),email);
    }
    @Test
    public void testGetCustomerByToken(){
        String token="n1MzRgkTPMYrzEa4nNgvYiZYvtjHxB";
        Customer customer= customerDao.findByResetPasswordToken(token);
        assertEquals(token,customer.getResetPasswordToken());
        assertNotNull(customer);
    }

    @Test
    public void testGetCustomerByFullName(){
        Customer customer=customerDao.getCustomerByfullName("Sanya Lad");
        System.out.println(customer);
    }

}