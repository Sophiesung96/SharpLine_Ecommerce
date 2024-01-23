package com.example.demo01.src.Service;

import com.example.demo01.src.Pojo.OrderStatus;
import com.example.demo01.src.Pojo.OrderTrack;

import java.util.List;

public interface OrderTrackService {

    void createOrderTrack(OrderTrack orderTrack);
    void updateOrderTrackByOrderId(OrderTrack orderTrack,int orderId);
    List<OrderTrack> findOrderTrackById(int id);
    List<OrderTrack> getCustomerTrackStatusList(int orderId);

}
