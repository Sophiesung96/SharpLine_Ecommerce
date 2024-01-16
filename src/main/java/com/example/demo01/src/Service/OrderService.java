package com.example.demo01.src.Service;

import com.example.demo01.src.Pojo.*;

import java.util.List;

public interface OrderService {

    List<Order> findAllByKeyword(int pageNo, String keyword);

    List<Order> findAll(int pageNo);

    List<Integer> getTotalPage();

    OrderDetailForm getOrderDetailById(int orderId);

    void DeleteOrderById(int orderId);

    OrderDetailForm createorder(Customer customer, Address address, List<CartItem> list
            , PaymentMethod paymentMethod, CheckOutInfo checkOutInfo);

    void updatePaymentMethod(String method, int id);

    void copyAddressFromCustomer(Order order, int customerId);

    void copyShippingAddress(Order order, Address address, Customer customer);

    Order getOrderById(int orderId);

    List<Country> listAllCountries();

    List<TableOrderDetail> getOrderDetailsList(int orderId);

    List<OrderDetails> getOrderDetailsByOrderId(int orderId);

    void createOrderDetail(Order order, Iterable<OrderDetails> list, Customer customer);

    void updateOrderDetailsByOrderId(OrderDetails orderDetails);

    void updateOriginalOrderById(Order order);

    void updateTrackStatus(String trackStatus, Order order);

    List<TableOrderDetail> getTrackStatusList(int orderId);

    List<Order> getOrderTrackByKeyword(String keyword, int pageNo);

    void updateStatus(int orderId, String status);

    List<CombinedOrderListForCustomer> getOrderListForCustomer(int customerId);

    List<Order> getOrderByCustomerId(int customerid);

    List<Integer> getTotalPageForCustomerOrderList(int customerid);
    List<ProductListForCustomer> getCustomerOrderDetailList(int customerId);



}