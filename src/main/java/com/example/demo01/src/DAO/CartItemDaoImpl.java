package com.example.demo01.src.DAO;

import com.example.demo01.src.Mapper.CartItemMapper;
import com.example.demo01.src.Mapper.CartItemPNameMapper;
import com.example.demo01.src.Pojo.CartItem;
import com.example.demo01.src.Pojo.CartItemPName;
import com.example.demo01.src.Pojo.Customer;
import com.example.demo01.src.Pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class CartItemDaoImpl implements  CartItemDao{

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<CartItem> findByCustomer(Customer customer) {
        String sql="select * from Cartitem where customer_id=:customerid";
        List<CartItem> list=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        map.put("customerid",customer.getId());
        list=namedParameterJdbcTemplate.query(sql,map,new CartItemMapper());
        if(list.size()>0){
            return list;
        }
        return null;

    }

    @Override
    public CartItem findByCustomerAndProduct(Customer customer, Product product) {
        String sql="select * from Cartitem where customer_id=:customerid and product_id=:productid";
        List<CartItem> list=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        map.put("customerid",customer.getId());
        map.put("productid",product.getId());
        list=namedParameterJdbcTemplate.query(sql,map,new CartItemMapper());
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }


    @Override
    public void DeleteByCustomerAndProduct(int customerId, int productId) {
        String sql="delete from Cartitem where customer_id=:customerid and product_id=:productid";
        List<CartItem> list=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        map.put("customerid",customerId);
        map.put("productid",productId);
       namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void updateQuantity(int quantity, int customerId, int productId) {
        String sql="update Cartitem set quantity=:quantity where customer_id=:customerid and product_id=:productid";
        List<CartItem> list=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        map.put("quantity",quantity);
        map.put("customerid",customerId);
        map.put("productid",productId);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void SaveCartItem(CartItem item) {
        String sql="insert into Cartitem(customer_id,product_id,quantity) values(:customerid,:productid,:quantity)";
        Map<String,Object> map=new HashMap<>();
        map.put("quantity",item.getQuantity());
        map.put("customerid",item.getCustomer());
        map.put("productid",item.getProduct());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
    }

    @Override
    public List<CartItemPName> getJoinedProductnCustomer(Customer customer) {
        String sql="select cart.quantity as quantity,cart.shippingCost as shippingCost," +
                "p.id as productId,p.discount_percent as discountPercent,p.width as width," +
                "p.length as length,p.height as height,p.weight as weight, p.list_price as price,p.name as productName," +
                " p.main_image as productImage, p.alias as productAlias" +
                " from CartItem cart inner join customers c on cart.customer_id = c.id inner join products p on cart.product_id = p.id\n" +
                "where cart.customer_id=:id;";
        List<CartItemPName> list=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        map.put("id",customer.getId());
        list=namedParameterJdbcTemplate.query(sql,map,new CartItemPNameMapper());
        if(list.size()>0){
            return list;
        }
        return null;
    }

    @Override
    public void removeProduct(int customerId,int productId) {
        String sql="delete from Cartitem where customer_id=:customerid and product_id=:productid ";
        Map<String,Object> map=new HashMap<>();
        map.put("customerid",customerId);
        map.put("productid",productId);
        namedParameterJdbcTemplate.update(sql,map);


    }
}
