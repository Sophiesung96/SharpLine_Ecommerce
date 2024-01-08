package com.example.demo01.src.Mapper;

import com.example.demo01.src.Pojo.TableOrderDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderStatusforShipperMapper implements RowMapper<TableOrderDetail> {
    @Override
    public TableOrderDetail mapRow(ResultSet resultSet, int i) throws SQLException {
        TableOrderDetail detail=new TableOrderDetail();
        detail.setId(resultSet.getInt("Orderid"));
        detail.setStatusCondition(resultSet.getString("StatusCondition"));
        return detail;
    }
}
