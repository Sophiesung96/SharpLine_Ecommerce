package com.example.demo01.src.DAO;

import com.example.demo01.src.Mapper.OrderTrackMapper;
import com.example.demo01.src.Pojo.OrderStatus;
import com.example.demo01.src.Pojo.OrderTrack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderTrackDAOImpl implements OrderTrackDAO {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void createOrderTrack(OrderTrack orderTrack) {
        String sql="insert into order_track(order_id,status,updated_time,notes) values(:orderid,:status,:updatedTime,:note)";
        Map<String,Object> map=new HashMap<>();
        map.put("orderid",orderTrack.getOrderId());
        map.put("status",orderTrack.getStatus());
        map.put("updatedTime",orderTrack.getUpdatedTime());
        map.put("note",orderTrack.getNotes());
        KeyHolder keyHolder=new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);

    }

    @Override
    public void updateOrderTrackByOrderId(OrderTrack orderTrack, int orderId) {
        String sql="update order_track set status=:status,updated_time=:updatedTime,notes=:note where order_id=:id";
        Map<String,Object> map=new HashMap<>();
        map.put("orderid",orderTrack.getOrderId());
        map.put("status",orderTrack.getStatus());
        map.put("updatedTime",orderTrack.getUpdatedTime());
        map.put("note",orderTrack.getNotes());
        map.put("id",orderId);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public List<OrderTrack> findOrderTrackById(int id) {
        String sql="select * from  order_track  where order_id=:orderId";
        Map<String,Object> map=new HashMap<>();
        map.put("orderId",id);
       List<OrderTrack> list= namedParameterJdbcTemplate.query(sql,map,new OrderTrackMapper());
       if(list.size()>0){
           return list;
       }
       return null;
    }


}
