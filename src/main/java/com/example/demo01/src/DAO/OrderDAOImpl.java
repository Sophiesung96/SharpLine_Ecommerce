package com.example.demo01.src.DAO;

import com.example.demo01.src.Mapper.*;
import com.example.demo01.src.Pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class OrderDAOImpl implements OrderDAO {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;



    @Override
    public List<Order> findAllByKeyword(int pageNo, String keyword) {
        String sql="SELECT * FROM orders" +
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
        String sql="select * from orders limit :pageno,10";
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
        String sql="select count(*) as total from orders";

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
                "o.city as city,o.status as status,o.product_cost as productCost, o.shipping_cost as shippingCost" +
                ",o.tax as tax, o.subtotal as subTotal,o.state as state,o.total as total,o.postal_code as postalCode,o.payment_method as paymentmethod" +
                ",o.country as country,o.deliver_days as deliverDays,o.deliver_date as deliverDate," +
                "c.enabled as enabled from orders o inner join customers c on o.customer_id=c.id where o.id=:orderid";
        Map<String,Object> map=new HashMap<>();
        map.put("orderid",orderId);
        List<OrderDetailForm> list=new ArrayList<>();
        list=namedParameterJdbcTemplate.query(sql,map,new OrderDetailFormMapper());
        if(list.size()>0){
            return list.get(0);

        }        return null;
    }

    @Override
    public void EditOrder(Order order) {
        String sql="update orders set customer_id=:customerid  where id=:orderid";
        Map<String,Object> map=new HashMap<>();
        map.put("orderid",order.getId());
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void DeleteOrderById(int orderId) {
        String sql="delete from orders where id=:orderid";
        Map<String,Object> map=new HashMap<>();
        map.put("orderid",orderId);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void updatePaymentMethod(String method,int id) {
        String sql="update  orders  set payment_method=:method where id=:orderid";
        Map<String,Object> map=new HashMap<>();
        map.put("method",method);
        map.put("orderid",id);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public int createorder(Customer customer, Order order) {
        String sql="insert into orders(customer_id,order_time,payment_method,product_cost,shipping_cost,subtotal," +
                "tax,total,status,first_name,last_name,phone_number,address_line1,address_line2,city,state,postal_code,country,deliver_days,deliver_date) " +
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
        String sql="select * from orders  where id=:id";
        Map<String,Object> map=new HashMap<>();
        map.put("id",orderId);
        List<Order>list= namedParameterJdbcTemplate.query(sql,map,new OrderMapper());
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Order> getOrderByCustomerId(int customerid) {
        String sql="select * from orders where customer_id=:customerid";
        Map<String,Object> map=new HashMap<>();
        map.put("customerid",customerid);
        List<Order>list= namedParameterJdbcTemplate.query(sql,map,new OrderMapper());
        if(list.size()>0){
            return list;
        }
        return null;
    }

    @Override
    public List<Country> listAllCountries() {
        String sql="select * from countries order by name asc";
        Map<String,Object> map=new HashMap<>();
       List<Country>list= namedParameterJdbcTemplate.query(sql,map,new CountryMapper());
       if(list.size()>0){
           return list;

       }
        return null;
    }


    @Override
    public List<TableOrderDetail> getOrderDetailsList(int orderId) {
        String sql="\n" +
                "    SELECT" +
                "        o.id as id," +
                "        o.order_time as orderTime," +
                "        o.customer_id as customerId," +
                "        c.email as email," +
                "        GROUP_CONCAT(p.name) as ProductNames," +
                "        GROUP_CONCAT(p.main_image) as ProductMainImage," +
                "        GROUP_CONCAT(p.id) as productIds," +
                "        GROUP_CONCAT(details.quantity) as Quantities," +
                "        GROUP_CONCAT(details.unit_price) as UnitPrices," +
                "        GROUP_CONCAT(details.subtotal) as Subtotals," +
                "        GROUP_CONCAT(details.product_cost) as DetailProductCosts," +
                "        GROUP_CONCAT(o.shipping_cost) as ShippingCosts," +
                "        GROUP_CONCAT(o.tax) as taxes," +
                "        GROUP_CONCAT(ca.name) as CategoryNames," +
                "        o.address_line1 as addressLine1," +
                "        o.address_line2 as addressLine2," +
                "        o.first_name as firstName," +
                "        o.last_name as lastName," +
                "        o.phone_number as phoneNumber," +
                "        o.city as city," +
                "        o.status as status," +
                "        o.product_cost as productCosts," +
                "        o.shipping_cost as shippingCost," +
                "        o.tax as tax," +
                "        o.state as state," +
                "        o.total as total," +
                "        o.postal_code as postalCode," +
                "        o.payment_method as paymentMethod," +
                "        o.country as country," +
                "        o.deliver_days as deliverDays," +
                "        o.deliver_date as deliverDate," +
                "        c.enabled as enabled" +
                "    FROM" +
                "        orders o" +
                "        INNER JOIN Order_details details ON o.id = details.order_id" +
                "        INNER JOIN products p ON p.id = details.product_id" +
                "        INNER JOIN customers c ON o.customer_id = c.id" +
                "        INNER JOIN categories ca ON ca.id = p.category_id" +
                "    WHERE" +
                "        o.id = :orderId" +
                "    GROUP BY" +
                "        o.id," +
                "        o.order_time," +
                "        o.customer_id," +
                "        c.email," +
                "        o.address_line1," +
                "        o.address_line2," +
                "        o.first_name," +
                "        o.last_name," +
                "        o.phone_number," +
                "        o.city," +
                "        o.status," +
                "        o.product_cost," +
                "        o.shipping_cost," +
                "        o.tax," +
                "        o.state," +
                "        o.total," +
                "        o.postal_code," +
                "        o.payment_method," +
                "        o.country," +
                "        o.deliver_days," +
                "        o.deliver_date," +
                "        c.enabled ";
        Map<String,Object> map=new HashMap<>();
        map.put("orderId",orderId);
        List<TableOrderDetail>list= namedParameterJdbcTemplate.query(sql,map,new OrderDetailJoinOrderMapper());
        if(list.size()>0){
            return list;
        }
        return null;
    }

    @Override
    public List<OrderDetails> getOrderDetailsByOrderId(int orderId) {
        String sql="select * from Order_details where order_Id=:orderid";
        Map<String,Object> map=new HashMap<>();
        map.put("orderid",orderId);
        List<OrderDetails> list=namedParameterJdbcTemplate.query(sql,map,new OrderDetailsMapper());
        if(list.size()>0){
            return list;

        }
        return null;
    }

    @Override
    public void updateOrderDetailsByOrderId(OrderDetails orderDetails) {
        String sql = "update Order_details set order_Id=:orderId, product_id=:productid, quantity=:quantity, unit_price=:unitprice, " +
                "subtotal=:subtotal, product_cost=:Productcost, shipping_cost=:shippingcost where id=:id";
        Map<String,Object> map=new HashMap<>();
        map.put("productid",orderDetails.getProductId());
        map.put("quantity",orderDetails.getQuantity());
        map.put("unitprice",orderDetails.getUnitPrice());
        map.put("subtotal",orderDetails.getSubTotal());
        map.put("Productcost",orderDetails.getProductCost());
        map.put("shippingcost",orderDetails.getShippingCost());
        map.put("orderId",orderDetails.getOrderId());
        map.put("id",orderDetails.getId());
        namedParameterJdbcTemplate.update(sql,map);

    }

    @Override
    public void updateOriginalOrderById(Order order) {
        String sql="update orders set customer_id=:customerid,order_time=:order_time,payment_method=:payment_method," +
                "product_cost=:product_cost,shipping_cost=:shipping_cost,subtotal=:subtotal," +
                "tax=:tax,total=:total,first_name=:firstname," +
                "last_name=:lastname,phone_number=:phonenumber,address_line1=:addressline1,address_line2=:addressline2,city=:city," +
                "state=:state,postal_code=:postal_code,country=:country,deliver_days=:deliver_days,deliver_date=:deliver_Date where id=:id";
        Map<String,Object> map=new HashMap<>();
        map.put("id",order.getId());
        map.put("customerid",order.getCustomerId());
        map.put("order_time",order.getOrderTime());
        map.put("payment_method",order.getPaymentMethod());
        map.put("tax",order.getTax());
        map.put("total",order.getTotal());
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
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void updateTrackStatus(String trackStatus,Order order) {
        String sql="update orders set status=:status where id=:orderid";
        Map<String,Object>map=new HashMap<>();
        map.put("status",trackStatus);
        map.put("orderid",order.getId());
        namedParameterJdbcTemplate.update(sql,map);

    }



    @Override
    public List<TableOrderDetail> getTrackStatusList(int orderId) {
        String sql="select o.id as Orderid,track.status as StatusCondition " +
                "from orders o " +
                "inner join order_track track on track.order_id=o.id " +
                "where o.id=:orderId and track.status<>'NEW' and track.status<>'PAID' " +
                "and track.status<>'PROCESSING' and track.status<>'CANCELED'";
        Map<String,Object>map=new HashMap<>();
        map.put("orderId",orderId);
      List<TableOrderDetail>list=namedParameterJdbcTemplate.query(sql,map,new OrderStatusforShipperMapper());
      if(list.size()>0){
          return list;

      }
      return null;
    }

    @Override
    public List<Order> getOrderTrackByKeyword(String keyword,int pageNo) {
        String sql = "SELECT * " +
                "FROM orders " +
                "WHERE " +
                " CONCAT('#',id) LIKE CONCAT('%', :keyword, '%') OR " +
                "CONCAT(first_name, ' ', last_name) LIKE CONCAT('%', :keyword, '%') " +
                "OR"+
                " first_name LIKE CONCAT('%', :keyword, '%') " +
                "OR last_name LIKE CONCAT('%', :keyword, '%') " +
                "OR address_line1 LIKE CONCAT('%', :keyword, '%') " +
                "OR address_line2 LIKE CONCAT('%', :keyword, '%') " +
                "OR postal_code LIKE CONCAT('%', :keyword, '%') " +
                "OR phone_number LIKE CONCAT('%', :keyword, '%') " +
                "OR payment_method LIKE CONCAT('%', :keyword, '%') " +
                "OR city LIKE CONCAT('%', :keyword, '%') " +
                "OR state LIKE CONCAT('%', :keyword, '%') " +
                "OR country LIKE CONCAT('%', :keyword, '%') " +
                "OR status LIKE CONCAT('%', :keyword, '%') " +
                "LIMIT :pageNo,10";


        Map<String,Object>map=new HashMap<>();
        map.put("keyword",keyword);
        map.put("pageNo",(pageNo-1)*10);
        List<Order>list=namedParameterJdbcTemplate.query(sql,map,new OrderMapper());
        if(list.size()>0){
            return list;

        }
        return null;
    }


    @Override
    public List<CombinedOrderListForCustomer> getOrderListForCustomer(int customerId) {
        String sql="select CombinedOrderListForCustomer.id as OrderId,GROUP_CONCAT(productName) as ProductName from (select o.id ,o.customer_id as customerId " +
                "     ,p.name as productName" +
                "       from orders o inner join Order_details details on o.id=details.order_id inner join products p on p.id=details.product_id " +
                "where o.customer_id=:customerId) CombinedOrderListForCustomer " +
                "group by OrderId";
        Map<String,Object>map=new HashMap<>();
        map.put("customerId",customerId);
        List<CombinedOrderListForCustomer> list=  namedParameterJdbcTemplate.query(sql,map,new CombinedOrderListForCustomerMapper());
        if(list.size()>0){
            return list;

        }
        return null;
    }

    @Override
    public Integer getTotalPageForCustomerOrderList(int customerId) {
        String sql="SELECT COUNT(1) as total " +
                "FROM (" +
                "    SELECT CombinedOrderListForCustomer.id AS OrderId, GROUP_CONCAT(productName) AS ProductName" +
                "    FROM ( " +
                "    SELECT o.id, o.customer_id AS customerId, p.name AS productName" +
                "    FROM orders o" +
                "    INNER JOIN Order_details details ON o.id = details.order_id" +
                "    INNER JOIN products p ON p.id = details.product_id" +
                "    WHERE o.customer_id = :customerId" +
                "    ) CombinedOrderListForCustomer" +
                "       GROUP BY OrderId " +
                "     )  testM;";

        Map<String,Object> map=new HashMap<>();
        map.put("customerId",customerId);
        PageNumber pageNumber=namedParameterJdbcTemplate.queryForObject(sql,map,new PageNumberMapper());
        if(pageNumber!=null){
            return pageNumber.getPagenumber();
        }
        return null;
    }



    public List<ProductListForCustomer> getCustomerOrderDetailList(int customerId,int orderId) {
        String sql=" SELECT" +
                "    SecondLayerQ.OrderId as OrderId," +
                "    GROUP_CONCAT(SecondLayerQ.MainImage) as MainImage, " +
                "    GROUP_CONCAT(SecondLayerQ.ProductId) as ProductId, " +
                "    GROUP_CONCAT(SecondLayerQ.Quantity) as Quantity," +
                "    GROUP_CONCAT(SecondLayerQ.Subtotal) as Subtotal," +
                "    GROUP_CONCAT(SecondLayerQ.Unitprice) as Unitprice," +
                "    GROUP_CONCAT(SecondLayerQ.ProductName) as ProductName," +
                "    GROUP_CONCAT(SecondLayerQ.ShippingCost) as ShippingCost," +
                "    GROUP_CONCAT(SecondLayerQ.ProductCost) as ProductCost" +
                "    FROM (" +
                "    SELECT" +
                "        CombinedOrderListForCustomer.id as OrderId," +
                "        GROUP_CONCAT(productName) as ProductName," +
                "        CombinedOrderListForCustomer.MainImage," +
                "        CombinedOrderListForCustomer.Quantity," +
                "        CombinedOrderListForCustomer.Subtotal," +
                "        CombinedOrderListForCustomer.Unitprice," +
                "        CombinedOrderListForCustomer.ProductId as ProductId," +
                "        CombinedOrderListForCustomer.ShippingCost," +
                "        CombinedOrderListForCustomer.ProductCost as ProductCost" +
                "    FROM (" +
                "        SELECT" +
                "            o.id," +
                "            o.customer_id as customerId," +
                "            details.shipping_cost as ShippingCost," +
                "            p.name as productName," +
                "            p.main_image as MainImage," +
                "            details.quantity as Quantity," +
                "            details.subtotal as Subtotal," +
                "            details.unit_price as Unitprice," +
                "            p.id as ProductId," +
                "            details.product_cost as ProductCost" +
                "        FROM" +
                "            orders o" +
                "            INNER JOIN Order_details details ON o.id = details.order_id" +
                "            INNER JOIN products p ON p.id = details.product_id" +
                "        WHERE" +
                "            o.customer_id = :customerId AND o.id = :orderId" +
                "    ) CombinedOrderListForCustomer" +
                "    GROUP BY OrderId, MainImage, Quantity, Subtotal, Unitprice, ProductId, ShippingCost, ProductCost" +
                ") SecondLayerQ" +
                " GROUP BY OrderId";
        Map<String,Object> map=new HashMap<>();
        map.put("customerId",customerId);
        map.put("orderId",orderId);
        List<ProductListForCustomer>list=namedParameterJdbcTemplate.query(sql,map,new ProductListForCustomerMapper());
        if(list.size()>0){
            return list;

        }
        return null;
    }

    @Override
    public Order getOrderDetailByIdAndCustomer(int customerId, int orderId) {
        String sql="select * from orders where id=:orderId and customer_id=:customerId";
        Map<String,Object> map=new HashMap<>();
        map.put("customerId",customerId);
        map.put("orderId",orderId);
        List<Order>list=namedParameterJdbcTemplate.query(sql,map,new OrderMapper());
        if(list.size()>0){
            return list.get(0);

        }
        return null;
    }

    @Override
    public List<TableOrderDetail> getCustomerTrackStatusList(int CustomerId, int OrderId) {
        String sql="select  o.id as Orderid,track.status as StatusCondition " +
                "    from orders o " +
                "    inner join order_track track on track.order_id=o.id " +
                "    where o.id=:orderId and o.customer_id=:customerId";
        Map<String,Object>map=new HashMap<>();
        map.put("orderId",OrderId);
        map.put("customerId",CustomerId);
        List<TableOrderDetail>list=namedParameterJdbcTemplate.query(sql,map,new OrderStatusforShipperMapper());
        if(list.size()>0){
            return list;

        }
        return null;
    }

    @Override
    public List<Order> findByOrderTimeBetween(Date startTime, Date endTime) {
       String sql="select * from orders where order_time between " +
               " :startTime and :endTime order by order_time asc";
       Map<String,Object>map=new HashMap<>();
       map.put("startTime",startTime);
       map.put("endTime",endTime);
     List<Order>list=namedParameterJdbcTemplate.query(sql,map,new OrderMapper());
     if(list.size()>0){
         return list;

     }
     return null;
    }

    @Override
    public List<TableOrderDetail> findOrderDetailListForGoogleChart(Date startDate, Date enddate) {
        String sql="select o.id as id,o.order_time as orderTime,o.customer_id as customerId,p.name as Productname, p.main_image as ProductmainImage," +
                "    p.id as productId,c.enabled as enabled,o.status as StatusCondition," +
                "     o.address_line1 as addressline1,o.address_line2 as addressline2,c.email as email," +
                "   o.first_name as firstName, o.last_name as lastName,o.phone_number as phoneNumber," +
                "   o.city as city,o.status as status,o.product_cost as productCost, o.shipping_cost as shippingCost" +
                "  ,o.tax as tax, o.state as state,o.total as total,o.postal_code as postalCode,o.payment_method as paymentmethod" +
                "  ,o.country as country,o.deliver_days as deliverDays,o.deliver_Date as deliverDate," +
                "    details.quantity as quantity,details.unit_price as unitPrice,details.subtotal as subTotal" +
                "  ,details.product_cost as DetailproductCost , ca.name as CategoryName from orders o" +
                "      inner join Order_details details on o.id=details.order_id" +
                "    inner join products p on p.id=details.product_id" +
                "   inner join categories ca on p.category_id =ca.id inner join customers c on c.id=o.customer_id" +
                " where o.order_time between :startTime and :endTime order by o.order_time asc";
        Map<String,Object>map=new HashMap<>();
        map.put("startTime",startDate);
        map.put("endTime",enddate);
      List<TableOrderDetail>list= namedParameterJdbcTemplate.query(sql,map,new TableOrderDetailMapper());
        if(list.size()>0){
            return list;

        }
        return null;
    }
}
