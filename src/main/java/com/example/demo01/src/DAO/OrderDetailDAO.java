package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.OrderDetails;
import com.example.demo01.src.Pojo.OrderStatus;
import com.example.demo01.src.Pojo.TableOrderDetail;

import java.util.Date;
import java.util.List;

public interface OrderDetailDAO {

    List<TableOrderDetail> findWithCategoryAndTimeBetween(Date startTime,Date endTIme);

    List<OrderDetails> CountCustomerOrderByProductIdAndOrderStatus(int productId, int customerId, OrderStatus orderStatus);
}
