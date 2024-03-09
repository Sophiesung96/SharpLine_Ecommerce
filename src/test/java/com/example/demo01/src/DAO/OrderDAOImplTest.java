package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.*;
import com.example.demo01.src.Service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.mock.web.SpringBootMockServletContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderDAOImplTest {
    @SpyBean
    OrderDAO orderDAO;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderTrackDAO orderTrackDAO;
    @Autowired
    OrderDetailDAO orderDetailDAO;


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
        order.setCustomerId(1);
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
        List<Order> testOrder= orderDAO.getOrderByCustomerId(1);
        System.out.println(order);
    testOrder.stream().forEach(Neworder->assertEquals(Neworder.getFirstName(),order.getFirstName()));
        testOrder.stream().forEach(Neworder->assertEquals(Neworder.getLastName(),order.getLastName()));
    }

    @Test
    public void testFindOrderDetailsList(){
        int orderId=1;
        List<TableOrderDetail> list=orderDAO.getOrderDetailsList(orderId);
       list.stream().filter(orderDetailForm -> orderDetailForm.getCustomerId()==1&&orderDetailForm.getFirstName().equals("David")
       && orderDetailForm.getLastName().equals("Fountaine")).collect(Collectors.toList());
       list.forEach(detail->assertEquals(1,detail.getCustomerId()));

    }

    @Test
    @Transactional
    public void updateOrderDetailsByOrderId(){
        OrderDetails orderDetails=new OrderDetails();
        orderDetails.setOrderId(18);
        orderDetails.setQuantity(5);
        orderDetails.setUnitPrice((float) 2469.13);
        orderDetails.setProductId(31);
        orderDetails.setSubTotal((float)12345.66);
        orderDetails.setProductCost((float)12345.66);
        orderDetails.setShippingCost((float)8.8);
        List<OrderDetails> list=orderDAO.getOrderDetailsByOrderId(18);
        orderService.updateOrderDetailsByOrderId(orderDetails);
       for(OrderDetails detail:list){
           assertNotEquals(detail.getProductCost(),orderDetails.getProductCost());
           assertNotNull(detail);

       }
    }
    @Test
    @Transactional
    public void testUpdateOrderStatus(){
        String trackStatus="NEW";
        Order order=new Order();
        order.setId(1);
        Order DBorder=orderService.getOrderById(order.getId());
        orderService.updateTrackStatus(trackStatus,order);
        assertNotEquals(DBorder.getStatus(),trackStatus);
    }

    @Test
    public void testgetTrackStatusList(){
        int orderId=1;
      List<TableOrderDetail>list= orderDAO.getTrackStatusList(orderId);
        TableOrderDetail details=new TableOrderDetail();
        details.setStatusCondition("PICKED");
      boolean yay=list.contains(details.getStatusCondition());
        boolean no=list.contains("PACKAGED");
        System.out.println(no);
        System.out.println(yay);
      list.stream().forEach(detail-> System.out.println(detail.getStatusCondition()));
    }

    @Test
    public void  testgetOrderTrackByKeyword(){
        String keyword="Sanya Lad";
        int pageNo=1;
       List<Order>list= orderDAO.getOrderTrackByKeyword(keyword,pageNo);
       assertNotNull(list);
       list.stream().forEach(detail-> assertEquals(2,detail.getCustomerId()));

    }

    @Test
    @Transactional
    public void test() {
        int orderId = 1;
        String status = "NEW";
        Order orderInDB = orderDAO.getOrderById(orderId);
        OrderStatus orderStatus = OrderStatus.valueOf(status);
        List<OrderTrack> orderTrackList = orderTrackDAO.findOrderTrackById(orderId);
        //if a new order Track has been added

        orderInDB.setStatus(status);
        OrderTrack NeworderTrack = new OrderTrack();
        NeworderTrack.setOrderId(orderId);
        NeworderTrack.setStatus(status);
        NeworderTrack.setNotes(orderStatus.defaultdescription());
        NeworderTrack.setUpdatedTime(new Date());
        orderTrackList.add(NeworderTrack);
        orderTrackDAO.createOrderTrack(NeworderTrack);
        orderDAO.updateTrackStatus(status, new Order(orderId));


    }
    @Test
    public void testgetTotalPageForCustomerOrderList(){
       Integer total= orderDAO.getTotalPageForCustomerOrderList(2);
        System.out.println(total);
        List<Integer> getTotalPageForCustomerOrderList=orderService.getTotalPageForCustomerOrderList(2);
        System.out.println(getTotalPageForCustomerOrderList.size());
    }

    @Test
    public void testGetCustomerOrderDetailList() {
        int customerId = 2;
        int orderId=2;
        List<ProductListForCustomer> list = orderDAO.getCustomerOrderDetailList(customerId,orderId);
        assertNotNull(list);
        list.stream().forEach(product -> System.out.println(product.getProductId()));
        list.stream().forEach(product -> System.out.println(product.getMainImage()));
         list.stream().forEach(product -> System.out.println("Sorted MainImage: "+product.getSortedMainImage()));

    }
    @Test
    public void testGetOrderDetailByIdAndCustomer(){
        int customerId = 2;
        int orderId=25;
      Order order= orderDAO.getOrderDetailByIdAndCustomer(customerId,orderId);
        System.out.println(order.getId());
    }

    @Test
    public void testfindByOrderTimeBetweenStartnEndTime() throws ParseException {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime=dateFormat.parse("2020-10-31 10:55:44");
        Date endTime=dateFormat.parse("2021-11-29 13:41:09");
        List<Order>list=orderDAO.findByOrderTimeBetween(startTime,endTime);
        list.stream().forEach(s-> System.out.println(s));
    }
    @Test
    public void testTableOrderDetail(){
        List<TableOrderDetail>list=orderDAO.getTrackStatusList(1);
        System.out.println(list);

    }
    @Test
    public void testCountCustomerOrderByProductIdAndOrderStatus(){
        int productId=5;
        int customerId=2;
        List<OrderDetails> list=orderDetailDAO.CountCustomerOrderByProductIdAndOrderStatus(productId,customerId,OrderStatus.DELIVERED);
        assertEquals(list.get(0).getCount(),1);
    }



}