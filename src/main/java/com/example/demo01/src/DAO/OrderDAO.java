package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.*;

import java.util.List;

public interface OrderDAO {

    public List<Order> findAllByKeyword(int pageNo, String keyword);
     public List<OrderDetails> getOrderDetailsByOrderId(int orderId);
     public List<Order> findAll(int pageNo);
     public Integer getTotalPage();
     public int createorder(Customer customer,Order order);
     public void createOrderDetail(Order order,Iterable<OrderDetails> list,Customer customer);
     public OrderDetailForm getOrderDetailById(int orderId);
    public Order getOrderById(int orderId);
    public Order getOrderByCustomerId(int customerid);
    void updatePaymentMethod(String method,int id);
     public void DeleteOrderById(int orderId);
     public void EditOrder(Order order);
     List<TableOrderDetail> getOrderDetailsList(int orderId);
     List<Country> listAllCountries();
     public void updateOrderDetailsByOrderId(OrderDetails orderDetails);
     public void updateOriginalOrderById(Order order);
     public void updateTrackStatus(String trackStatus,Order order);
     public List<TableOrderDetail> getTrackStatusList(int orderId);
     public List<Order> getOrderTrackByKeyword(String keyword,int pageNo);



}
