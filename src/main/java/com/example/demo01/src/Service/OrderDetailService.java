package com.example.demo01.src.Service;

import com.example.demo01.src.Pojo.TableOrderDetail;

import java.util.Date;
import java.util.List;

public interface OrderDetailService {
    List<TableOrderDetail> findWithCategoryAndTimeBetween(Date startTime, Date endTIme);
}
