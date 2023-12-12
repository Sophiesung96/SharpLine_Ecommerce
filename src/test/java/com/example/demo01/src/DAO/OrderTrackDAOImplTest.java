package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.OrderStatus;
import com.example.demo01.src.Pojo.OrderTrack;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrderTrackDAOImplTest {

    @SpyBean
    OrderTrackDAO orderTrackDAO;

    @Test
    public void testUpdateOrderTrackByOrderId(){
         int orderId=1;
        OrderTrack orderTrack=new OrderTrack(1,1, OrderStatus.NEW.name(),new Date(),OrderStatus.NEW.defaultdscription());
         orderTrackDAO.updateOrderTrackByOrderId(orderTrack,1);
    }

    @Test
    public void testFindOrderTrackByOrderId(){
        int orderId=1;
       List<OrderTrack> list= orderTrackDAO.findOrderTrackById(orderId);
       assertNotNull(list);
       list.stream().forEach(orderTrack -> System.out.println(orderTrack));
    }

}