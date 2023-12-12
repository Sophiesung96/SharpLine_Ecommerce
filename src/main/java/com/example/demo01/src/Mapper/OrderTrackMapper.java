package com.example.demo01.src.Mapper;

import com.example.demo01.src.Pojo.OrderTrack;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderTrackMapper implements RowMapper<OrderTrack> {
    @Override
    public OrderTrack mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderTrack orderTrack=new OrderTrack();
        orderTrack.setId(resultSet.getInt("id"));
        orderTrack.setOrderId(resultSet.getInt("order_id"));
        orderTrack.setStatus(resultSet.getString("status"));
        orderTrack.setUpdatedTime(resultSet.getTimestamp("updated_time"));
        orderTrack.setNotes(resultSet.getString("notes"));
        return orderTrack;
    }
}
