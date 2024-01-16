package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.*;

import java.util.List;

public interface OrderDAO {

     List<Order> findAllByKeyword(int pageNo, String keyword);
      List<OrderDetails> getOrderDetailsByOrderId(int orderId);
      List<Order> findAll(int pageNo);
      Integer getTotalPage();
      int createorder(Customer customer,Order order);
      void createOrderDetail(Order order,Iterable<OrderDetails> list,Customer customer);
      OrderDetailForm getOrderDetailById(int orderId);
     Order getOrderById(int orderId);
     List<Order> getOrderByCustomerId(int customerid);
    void updatePaymentMethod(String method,int id);
      void DeleteOrderById(int orderId);
      void EditOrder(Order order);
     List<TableOrderDetail> getOrderDetailsList(int orderId);
     List<Country> listAllCountries();
      void updateOrderDetailsByOrderId(OrderDetails orderDetails);
      void updateOriginalOrderById(Order order);
      void updateTrackStatus(String trackStatus,Order order);
      List<TableOrderDetail> getTrackStatusList(int orderId);
      List<Order> getOrderTrackByKeyword(String keyword,int pageNo);
      List<CombinedOrderListForCustomer> getOrderListForCustomer(int customerId);
      Integer getTotalPageForCustomerOrderList(int customerId);
    List<ProductListForCustomer> getCustomerOrderDetailList(int customerId);




}
