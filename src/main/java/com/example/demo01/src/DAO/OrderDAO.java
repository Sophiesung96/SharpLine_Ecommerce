package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.*;

import java.util.List;

public interface OrderDAO {

    public List<Order> findAllByKeyword(int pageNo, String keyword);

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
     List<OrderDetailForm> getOrderDetailsList(int orderId);
     List<Country> listAllCountries();


}
