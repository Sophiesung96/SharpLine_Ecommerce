package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.OrderTrackDAO;
import com.example.demo01.src.Pojo.OrderTrack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderTrackServiceImpl implements OrderTrackService {

    @Autowired
    OrderTrackDAO orderTrackDAO;

    @Override
    public void createOrderTrack(OrderTrack orderTrack) {
      orderTrackDAO.createOrderTrack(orderTrack);
    }

    @Override
    public void updateOrderTrackByOrderId(OrderTrack orderTrack, int orderId) {
       orderTrackDAO.updateOrderTrackByOrderId(orderTrack, orderId);
    }

    @Override
    public List<OrderTrack> findOrderTrackById(int id) {
        List<OrderTrack> list=orderTrackDAO.findOrderTrackById(id);
        return list;
    }
}
