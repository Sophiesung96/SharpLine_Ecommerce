package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.TableOrderDetail;

import java.util.Date;
import java.util.List;

public interface OrderDetailDAO {

    List<TableOrderDetail> findWithCategoryAndTimeBetween(Date startTime,Date endTIme);
}
