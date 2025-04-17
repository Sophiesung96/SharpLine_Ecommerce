package com.example.demo01.src.DAO;

import com.example.demo01.src.Mapper.CountCustomerOrderDetailMapper;
import com.example.demo01.src.Mapper.OrderDetailsMapper;
import com.example.demo01.src.Mapper.ProductListForCustomerMapper;
import com.example.demo01.src.Mapper.TableOrderDetailMapper;
import com.example.demo01.src.Pojo.OrderDetails;
import com.example.demo01.src.Pojo.OrderStatus;
import com.example.demo01.src.Pojo.ProductListForCustomer;
import com.example.demo01.src.Pojo.TableOrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDetailDAOImpl implements OrderDetailDAO{

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public List<TableOrderDetail> findWithCategoryAndTimeBetween(Date startTime, Date endTIme) {
        String sql="select o.id as id,o.order_time as orderTime,o.customer_id as customerId,c.email as email,p.name as Productname, p.main_image as ProductmainImage," +
                "p.id as productId," +
                "o.address_line1 as addressline1,o.address_line2 as addressline2," +
                "o.first_name as firstName, o.last_name as lastName,o.phone_number as phoneNumber," +
                "o.city as city,o.status as status,o.product_cost as productCost, o.shipping_cost as shippingCost" +
                ",o.tax as tax, o.state as state,o.total as total,o.postal_code as postalCode,o.payment_method as paymentmethod" +
                ",o.country as country,o.deliver_days as deliverDays,o.deliver_date as deliverDate," +
                "c.enabled as enabled, details.quantity as quantity,details.unit_price as unitPrice,details.subtotal as subTotal " +
                ",details.product_cost as DetailproductCost,track.status as StatusCondition , ca.name as CategoryName from orders o inner join Order_details details on o.id=details.order_id  inner join products p on p.id=details.product_id " +
                "inner join customers c on o.customer_id=c.id " +
                "inner join categories ca on p.category_id =ca.id" +
                " inner join order_track track on o.id=track.order_id "+
                " where o.order_time between :startTime and :endTIme order by o.order_time asc";
        Map<String,Object> map=new HashMap<>();
        map.put("startTime",startTime);
        map.put("endTIme",endTIme);
        List<TableOrderDetail>list= namedParameterJdbcTemplate.query(sql,map,new TableOrderDetailMapper());
        if(list.size()>0){
            return list;
        }
        return null;
    }

    @Override
    public List<OrderDetails> CountCustomerOrderByProductIdAndOrderStatus(int productId, int customerId, OrderStatus orderStatus) {
        String sql="select count(*) as ReviewNum from Order_details d inner join orders o on o.id=d.order_Id  where d.product_id=:productId and  o.customer_id=:customerId and o.status='DELIVERED'";
        Map<String,Object>map=new HashMap<>();
        map.put("productId",productId);
        map.put("customerId",customerId);
        map.put("status",orderStatus.name());
        List<OrderDetails> list=namedParameterJdbcTemplate.query(sql,map,new CountCustomerOrderDetailMapper());
        if(list.size()>0){
            return list;

        }
        return null;
    }


}
