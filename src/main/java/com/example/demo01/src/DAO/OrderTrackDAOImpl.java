package com.example.demo01.src.DAO;

import com.example.demo01.src.Mapper.OrderStatusforShipperMapper;
import com.example.demo01.src.Mapper.OrderTrackMapper;
import com.example.demo01.src.Pojo.OrderStatus;
import com.example.demo01.src.Pojo.OrderTrack;
import com.example.demo01.src.Pojo.TableOrderDetail;
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
        String sql="update order_track set status=:orderStatus,updated_time=:updatedTime,notes=:note where order_id=:orderid and id=:id";
        Map<String,Object> map=new HashMap<>();
        map.put("id",orderTrack.getOrderId());
        map.put("orderStatus",orderTrack.getStatus());
        map.put("updatedTime",orderTrack.getUpdatedTime());
        map.put("note",orderTrack.getNotes());
        map.put("orderid",orderId);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public List<OrderTrack> findOrderTrackById(int id) {
        String sql="select * from  order_track  where order_id=:orderId order by updated_time asc";
        Map<String,Object> map=new HashMap<>();
        map.put("orderId",id);
       List<OrderTrack> list= namedParameterJdbcTemplate.query(sql,map,new OrderTrackMapper());
       if(list.size()>0){
           return list;
       }
       return null;
    }

    @Override
    public List<OrderTrack> getTrackStatusList(int orderId) {
            String sql="select o.id as Orderid,track.status as StatusCondition " +
                    "from `Order` o " +
                    "inner join order_track track on track.order_id=o.id " +
                    "where o.id=:orderId and track.status<>'NEW' and track.status<>'PAID' " +
                    "and track.status<>'PROCESSING' and track.status<>'CANCELED'";
            Map<String,Object>map=new HashMap<>();
            map.put("orderId",orderId);
            List<OrderTrack>list=namedParameterJdbcTemplate.query(sql,map,new OrderTrackMapper());
            if(list.size()>0){
                return list;

            }
            return null;
    }

    @Override
    public List<OrderTrack> getCustomerTrackStatusList(int orderId) {
        String sql="select * from  order_track  track  where order_id=:orderId and track.status<>'PAID' " +
                "" +
                "    and track.status<>'PROCESSING' and track.status<>'NEW'and track.status<>'CANCELED'order by updated_time asc";
        Map<String,Object>map=new HashMap<>();
        map.put("orderId",orderId);
        List<OrderTrack>list=namedParameterJdbcTemplate.query(sql,map,new OrderTrackMapper());
        if(list.size()>0){
            return list;

        }
        return null;
    }
}
