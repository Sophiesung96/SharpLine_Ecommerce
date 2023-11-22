package com.example.demo01.DAO;

import com.example.demo01.src.DAO.OrderDAO;
import com.example.demo01.src.Pojo.Customer;
import com.example.demo01.src.Pojo.Order;
import com.example.demo01.src.Pojo.OrderDetailForm;
import com.example.demo01.src.Pojo.OrderDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrderDAOImplTest {
    @SpyBean
    OrderDAO orderDAO;
    @Autowired
    ObjectMapper objectMapper;


    @Test
    public void testfindAll(){
        List<Order> list1=orderDAO.findAllByKeyword(1,"J");
        List<Order> list2=orderDAO.findAllByKeyword(1,"COD");
        List<Order> list3=orderDAO.findAllByKeyword(1,"321 Pine Ave");
        List<Order> list4=orderDAO.findAllByKeyword(1,"Apt 2A");
        System.out.println(list1);
        System.out.println(list2);
        System.out.println(list3);
        System.out.println(list4);
    }
    @Test
    public void testgetTotalPage(){
        int p=orderDAO.getTotalPage();
        int total=(p/10)+1;
        System.out.println(total);

    }
    @Test
    public void testGetOrderDetailsByOrderId(){
        OrderDetailForm order=orderDAO.getOrderDetailById(2);
      assertEquals(2,order.getId());
    }

    @Test
    @Transactional
    public void testDeletOrderById(){
        orderDAO.DeleteOrderById(2);
      OrderDetailForm order=orderDAO.getOrderDetailById(2);
      //check whether this order has been deleted
      assertNull(order);
    }
    @Test
    @Transactional
    public void testupdatePaymentMethod(){
        orderDAO.updatePaymentMethod("COD",1);
        OrderDetailForm order= orderDAO.getOrderDetailById(1);
        assertEquals("COD",order.getPaymentMethod());
    }
    @Test
    @Transactional
    public void testCreateorder(){
        Customer customer=new Customer(2);
        Order order=new Order();
        order.setId(9);
        order.setCity("SanFrisco");
        order.setCountry("USA");
        order.setState("California");
        order.setProductCost(100);
        order.setPaymentMethod("Credit Card");
        order.setPostalCode("1234");
        order.setAddressline1("asdgdfgdfgdfgdfgdf");
        order.setAddressline2("asdgdfgdfgdfgdfgdf");
        order.setPhoneNumber("012345678");
        order.setOrderTime("2020-11-03");
        order.setFirstName("John");
        order.setLastName("Doe");
        order.setShippingCost(0.99F);
        order.setSubTotal(20.99F);
        order.setDeliverDays(2);
        order.setDeliverDate(new Date());
        order.setStatus("NEW");
       orderDAO.createorder(customer,order);
    Order testOrder= orderDAO.getOrderByCustomerId(2);
        System.out.println(order);
    assertEquals(testOrder.getFirstName(),order.getFirstName());
    assertEquals(testOrder.getLastName(),order.getLastName());
    }



}