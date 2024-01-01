package com.example.demo01.src.Service;

import com.example.demo01.src.Pojo.*;

import java.util.List;

public interface OrderService {

    public List<Order> findAllByKeyword(int pageNo, String keyword);

    public List<Order> findAll(int pageNo);
    public List<Integer> getTotalPage();
    public OrderDetailForm getOrderDetailById(int orderId);
    public void DeleteOrderById(int orderId);
    public OrderDetailForm createorder(Customer customer, Address address, List<CartItem>list
            , PaymentMethod paymentMethod, CheckOutInfo checkOutInfo);
    void updatePaymentMethod(String method,int id);
    public void copyAddressFromCustomer(Order order,int customerId);
    public void copyShippingAddress(Order order,Address address,Customer customer);
    public Order getOrderById(int orderId);
    List<Country> listAllCountries();
    List<TableOrderDetail> getOrderDetailsList(int orderId);
    public List<OrderDetails> getOrderDetailsByOrderId(int orderId);
    public void createOrderDetail(Order order,Iterable<OrderDetails> list, Customer customer);
    public void updateOrderDetailsByOrderId(OrderDetails orderDetails);
    public void updateOriginalOrderById(Order order);
    public void updateTrackStatus(String trackStatus,Order order);
}
