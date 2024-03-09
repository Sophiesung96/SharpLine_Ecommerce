package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.OrderDetails;
import com.example.demo01.src.Pojo.OrderStatus;
import com.example.demo01.src.Pojo.TableOrderDetail;
import com.example.demo01.src.Service.OrderDetailService;
import com.example.demo01.src.Service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrderDetailDAOImplTest {

    @Autowired
    OrderDetailDAO orderDetailDAO;

    @Autowired
    ReviewService reviewService;
    @Autowired
    OrderDetailService orderDetailService;

    @Test
    public void testFindWithCategoryAndTimeBetween() throws ParseException {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime=dateFormat.parse("2020-10-31 10:55:44");
        Date endTime=dateFormat.parse("2021-11-29 13:41:09");
        List<TableOrderDetail> list=orderDetailDAO.findWithCategoryAndTimeBetween(startTime,endTime);
        list.stream().forEach(orderDetail-> System.out.println(orderDetail));
        assertNotNull(list);

    }


}