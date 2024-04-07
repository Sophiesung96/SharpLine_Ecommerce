package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.OrderDetailDAO;
import com.example.demo01.src.Pojo.OrderDetails;
import com.example.demo01.src.Pojo.OrderStatus;
import com.example.demo01.src.Pojo.TableOrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderDetailServiceImpl implements  OrderDetailService{

    @Autowired
    OrderDetailDAO orderDetailDAO;

    @Override
    public List<TableOrderDetail> findWithCategoryAndTimeBetween(Date startTime, Date endTIme) {
        List<TableOrderDetail> list=orderDetailDAO.findWithCategoryAndTimeBetween(startTime, endTIme);
        return list;
    }


}
