package com.example.demo01.src.DAO;

import com.example.demo01.src.Mapper.OrderDetailFormMapper;
import com.example.demo01.src.Mapper.OrderMapper;
import com.example.demo01.src.Mapper.PageNumberMapper;
import com.example.demo01.src.Pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDAOImpl implements OrderDAO {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;



    @Override
    public List<Order> findAllByKeyword(int pageNo, String keyword) {
        String sql="SELECT * FROM `Order`" +
                "WHERE first_name LIKE CONCAT('%', :keyword, '%') OR last_name LIKE CONCAT('%', :keyword, '%')" +
                "OR address_line1 LIKE CONCAT('%', :keyword, '%')"+"OR address_line2 LIKE CONCAT('%', :keyword, '%')"
                +"OR payment_method LIKE CONCAT('%', :keyword, '%')"+
                "OR status LIKE CONCAT('%', :keyword, '%')"+
                "order by order_time desc LIMIT :pageno, 10 ";
        Map<String,Object> map=new HashMap<>();
        map.put("keyword",keyword);
        map.put("pageno",(pageNo-1)*10);
        List<Order> list=  namedParameterJdbcTemplate.query(sql,map,new OrderMapper());

        return list;
    }

    @Override
    public List<Order> findAll(int pageNo) {
        String sql="select * from `Order` limit :pageno,10";
        List<Order>list=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        map.put("pageno",(pageNo-1)*10);
        list=namedParameterJdbcTemplate.query(sql,map,new OrderMapper());
        if(list.size()>0){
            return list;

        }
        return null;
    }

    @Override
    public Integer getTotalPage() {
        String sql="select count(*) as total from `Order`";

        Map<String,Object> map=new HashMap<>();
        PageNumber pageNumber=namedParameterJdbcTemplate.queryForObject(sql,map,new PageNumberMapper());
        if(pageNumber!=null){
           return pageNumber.getPagenumber();
        }
        return null;
    }

    @Override
    public OrderDetailForm getOrderDetailById(int orderId) {
        String sql="select o.id as id,o.order_time as orderTime,o.customer_id as customerId,c.email as email," +
                "o.address_line1 as addressline1,o.address_line2 as addressline2," +
                "o.first_name as firstName, o.last_name as lastName,o.phone_number as phoneNumber," +
                "o.city as city, o.state as state,o.total as total,o.postal_code as postalCode,o.payment_method as paymentmethod,o.country as country," +
                "c.enabled as enabled from `Order` o inner join customers c on o.customer_id=c.id where o.id=:orderid";
        Map<String,Object> map=new HashMap<>();
        map.put("orderid",orderId);
        List<OrderDetailForm> list=new ArrayList<>();
        list=namedParameterJdbcTemplate.query(sql,map,new OrderDetailFormMapper());
        if(list.size()>0){
            return list.get(0);

        }        return null;
    }



    @Override
    public void DeleteOrderById(int orderId) {
        String sql="delete from `Order` where id=:orderid";
        Map<String,Object> map=new HashMap<>();
        map.put("orderid",orderId);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void updatePaymentMethod(String method,int id) {
        String sql="update  `Order` set payment_method=:method where id=:orderid";
        Map<String,Object> map=new HashMap<>();
        map.put("method",method);
        map.put("orderid",id);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public int createorder(Customer customer, Order order) {
        String sql="insert into `Order`(customer_id,order_time,payment_method,product_cost,shipping_cost,subtotal," +
                "tax,total,status,first_name,last_name,phone_number,address_line1,address_line2,city,state,postal_code,country,deliver_days,deliver_Date) " +
                "values(:customer_id,:order_time,:payment_method,:product_cost,:shipping_cost,:subtotal," +
                ":tax,:total,:status,:firstname,:lastname,:phonenumber,:addressline1,:addressline2,:city," +
                ":state,:postal_code,:country,:deliver_days,:deliver_Date)";
        Map<String,Object> map=new HashMap<>();
        map.put("customer_id",customer.getId());
        map.put("order_time",order.getOrderTime());
        map.put("payment_method",order.getPaymentMethod());
        map.put("tax",order.getTax());
        map.put("total",order.getTotal());
        map.put("status",order.getStatus());
        map.put("firstname",order.getFirstName());
        map.put("lastname",order.getLastName());
        map.put("phonenumber",order.getPhoneNumber());
        map.put("addressline1",order.getAddressline1());
        map.put("addressline2",order.getAddressline2());
        map.put("city",order.getCity());
        map.put("product_cost",order.getProductCost());
        map.put("shipping_cost",order.getShippingCost());
        map.put("subtotal",order.getSubTotal());
        map.put("state",order.getState());
        map.put("postal_code",order.getPostalCode());
        map.put("country",order.getCountry());
        map.put("deliver_days",order.getDeliverDays());
        map.put("deliver_Date",order.getDeliverDate());
        KeyHolder keyHolder=new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        int orderId=keyHolder.getKey().intValue();
        return orderId;
    }

    @Override
    public void createOrderDetail(Order order,Iterable<OrderDetails> list, Customer customer) {
         String sql="insert into Order_details(order_Id,product_id,quantity,unit_price,subtotal,product_cost,shipping_cost)" +
                 "values(:orderid,:productid,:quantity,:unitprice,:subtotal,:productcost,:shippingcost) ";
        Map<String,Object> map=new HashMap<>();
        map.put("orderid",order.getId());
        for(OrderDetails detail:list){
            map.put("productid",detail.getProductId());
            map.put("quantity",detail.getQuantity());
            map.put("unitprice",detail.getUnitPrice());
            map.put("subtotal",detail.getSubTotal());
            map.put("productcost",detail.getProductCost());
            map.put("shippingcost",detail.getShippingCost());
        }
        KeyHolder keyHolder=new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);

    }

    @Override
    public Order getOrderById(int orderId) {
        String sql="select * from `Order` where id=:id";
        Map<String,Object> map=new HashMap<>();
        map.put("id",orderId);
        List<Order>list= namedParameterJdbcTemplate.query(sql,map,new OrderMapper());
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public Order getOrderByCustomerId(int customerid) {
        String sql="select * from `Order` where customer_id=:customerid";
        Map<String,Object> map=new HashMap<>();
        map.put("customerid",customerid);
        List<Order>list= namedParameterJdbcTemplate.query(sql,map,new OrderMapper());
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
