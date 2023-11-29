package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.AddressDAO;
import com.example.demo01.src.Pojo.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AddressServiceImplTest {
     @Autowired
    AddressDAO addressDAO;

  @Autowired
PasswordEncoder passwordEncoder;

     @BeforeEach
     @Test
     public void testSetDefaultAddress(){
         int addressid=2;
         int customerId=2;
         Address address= addressDAO.findefaultAddressById(customerId);
         if(addressid>1){
         if(address!=null){
             //retrieve the previous default and make it not
             addressDAO.setNonDefaultForOther(address.getId(),address.getCustomerId());
             //set the new address as default
             addressDAO.setDefaultAddress(addressid,customerId);
         }
         //make the new address as default
         //but the previous one is primary
         else{
             addressDAO.setDefaultAddress(addressid,customerId);
         }
         //setting primary as a default address
     }else{
        addressDAO.setDefaultAddress4Primary(addressid,customerId);

    }
}
    @Test
    public void testFindefaultAddressById(){
        Address address=new Address();
        address= addressDAO.findefaultAddressById(2);
        assertNotEquals(0,address.getDefaultAddress());
        assertEquals(1,address.getDefaultAddress());
    }


    @Test
    public void test(){
        String testing="testing01";
        String newtest=passwordEncoder.encode(testing);
        System.out.println(newtest);
    }



}