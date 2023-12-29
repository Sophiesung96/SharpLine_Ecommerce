package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.OrderStatus;
import com.example.demo01.src.Pojo.OrderTrack;

import java.util.List;

public interface OrderTrackDAO {

    void createOrderTrack(OrderTrack orderTrack);
    void updateOrderTrackByOrderId(OrderTrack orderTrack,int orderId);
    List<OrderTrack> findOrderTrackById(int id);

}
