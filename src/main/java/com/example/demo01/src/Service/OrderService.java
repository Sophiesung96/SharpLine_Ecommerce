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
    List<OrderDetailForm> getOrderDetailsList(int orderId);
}
