package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.Address;
import com.example.demo01.src.Pojo.Customer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressDAOImplTest {
 @SpyBean
 AddressDAO addressDAO;


    @Test
    public void testFindByCustomerId(){
        int id=5;
        Address address=new Address();
        address.setCustomerId(5);
        address.setLastName("Doe");
        List<Address> list=new ArrayList<>();
        list.add(address);
        Mockito.when(addressDAO.findByCustomer(new Customer(1))).thenReturn(list);
        assertEquals(5,list.get(0).getCustomerId());
    }

    @Test
    public void testFindByCustomerIdnAddressId(){
        int customerid=5;
        int AddressId=1;
        Address address=new Address();
        address.setCustomerId(5);
        address.setLastName("Doe");
        Mockito.when(addressDAO.findByIdAndCustomer(AddressId,new Customer(5))).thenReturn(address);
        assertEquals(5,address.getCustomerId());
    }
    @Test
    @Transactional
    public void testUpdateAddressByCustomerIdnAddressId(){
        Address expectaddress=new Address();
        expectaddress.setAddressline2(null);
        expectaddress.setAddressline1("hahaha");
        expectaddress.setState("aaa");
        expectaddress.setCustomerId(2);
        expectaddress.setId(7);
        expectaddress.setDefaultAddress(1);
        expectaddress.setPostalCode("110");
        expectaddress.setCountryId(5);
        expectaddress.setFirstName("ho");
        expectaddress.setLastName("philip");;
        addressDAO.updateAddress(expectaddress);
        Address actualaddress=  addressDAO.findByIdAndCustomer(7,new Customer(2));
        assertEquals(expectaddress.getCustomerId(),actualaddress.getCustomerId());
        assertEquals(expectaddress.getId(),actualaddress.getId());
        assertEquals(expectaddress.getAddressline1(),actualaddress.getAddressline1());
        assertEquals(expectaddress.getPostalCode(),actualaddress.getPostalCode());
        assertEquals(expectaddress.getDefaultAddress(),actualaddress.getDefaultAddress());
    }

    @Test
    @Transactional
    public void testDeleteAddressByCustomerIdnAddressId() {
            addressDAO.deleteAddress(1,2);

    }

    @Test
    @Transactional
    public void tessettDefaultByAddressId()  {
        addressDAO.setDefaultAddress(3,2);
        Address address=  addressDAO.findByIdAndCustomer(3,new Customer(2));
        assertEquals(1,address.getDefaultAddress());

    }

    @Test
    @Transactional
    public void testsetNonDefault(){
        addressDAO.setNonDefaultForOther(2,2);
        Address address=  addressDAO.findByIdAndCustomer(2,new Customer(2));
        assertEquals(0,address.getDefaultAddress());
    }

    @Test
    public void testFindDefault(){
        Address address= addressDAO.findefaultAddressById(2);
       assertNull(address);
    }

    @Test
    @Transactional
    public void setDefaultAddress4Primary(){

        addressDAO.setDefaultAddress4Primary(2,2);
       List<Address>list= addressDAO.findByCustomer(new Customer(2));
       list.forEach(address -> assertEquals(0,address.getDefaultAddress()));
    }
    public String getMyDefault() {
        System.out.println("Getting Default Value");
        return "Default Value";
    }


    @Test
    public void whenOrElseGetAndOrElseDiffer_thenCorrect() {
        String text = "Text present";

        System.out.println("Using orElseGet:");
        String defaultText
                = Optional.ofNullable(text).orElseGet(this::getMyDefault);
        assertEquals("Text present", defaultText);

        System.out.println("Using orElse:");
        defaultText = Optional.ofNullable(text).orElse(getMyDefault());
        assertEquals("Text present", defaultText);
    }



}