package com.example.demo01.src.DAO;

import com.example.demo01.src.Mapper.AddressMapper;
import com.example.demo01.src.Pojo.Address;
import com.example.demo01.src.Pojo.Customer;
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

@Repository("AddressDAOImpl")
public class AddressDAOImpl implements AddressDAO {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Address> findByCustomer(Customer customer) {
        String sql = "select * from address where customer_id=:id";
        List<Address> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("id", customer.getId());
        list = namedParameterJdbcTemplate.query(sql, map, new AddressMapper());
        if (list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    public Address findByIdAndCustomer(Integer id, Customer customer) {
        String sql = "select * from address where customer_id=:customerid and id=:id";
        List<Address> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("customerid", customer.getId());
        list = namedParameterJdbcTemplate.query(sql, map, new AddressMapper());
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


    @Override
    public void DeleteByIdAndCustomer(Integer id, Customer customer) {
        String sql = "delete from address where id=:id, customer_id=:customerid";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("customerid", customer.getId());
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void CreateNewCustomer(Address address, Integer customerId) {
        String sql = "insert into address(customer_id,first_name,last_name,phone_number,address_line1,address_line2,city,state,country_id" +
                ",postal_code,default_address) values(:customerid,:firstname,:lastname,:phonenumber," +
                ":addressline1,:addressline2,:city,:state,:countryid,:postalcode,:defaultaddress)";
        Map<String, Object> map = new HashMap<>();
        map.put("customerid", customerId);
        map.put("firstname", address.getFirstName());
        map.put("lastname", address.getLastName());
        map.put("phonenumber", address.getPhoneNumber());
        map.put("addressline1", address.getAddressline1());
        map.put("addressline2", address.getAddressline2());
        map.put("city", address.getCity());
        map.put("state", address.getState());
        map.put("countryid", address.getCountryId());
        map.put("postalcode", address.getPostalCode());
        map.put("defaultaddress", 0);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
    }

    @Override
    public Address ShowCustomerforedit(Integer addressid, Integer customerId) {
        String sql = "select * from address where id=:id and customer_id=:customerId";
        Map<String, Object> map = new HashMap<>();
        map.put("customerId", customerId);
        map.put("id", addressid);
        Address address = namedParameterJdbcTemplate.queryForObject(sql, map, new AddressMapper());
        if (address != null) {
            return address;
        }
        return null;
    }

    @Override
    public void updateAddress(Address address) {
        String sql = "update address set customer_id=:customerid, first_name=:firstname, last_name=:lastname," +
                "phone_number=:phonenumber, address_line1=:addressline1, address_line2=:addressline2," +
                "city=:city, state=:state,country_id=:countryid,postal_code=:postalcode," +
                "default_address=:defaultaddress where id=:id and customer_id=:customerid";
        Map<String, Object> map = new HashMap<>();
        map.put("id", address.getId());
        map.put("customerid", address.getCustomerId());
        map.put("firstname", address.getFirstName());
        map.put("lastname", address.getLastName());
        map.put("phonenumber", address.getPhoneNumber());
        map.put("addressline1", address.getAddressline1());
        map.put("addressline2", address.getAddressline2());
        map.put("city", address.getCity());
        map.put("state", address.getState());
        map.put("countryid", address.getCountryId());
        map.put("postalcode", address.getPostalCode());
        map.put("defaultaddress", 0);
        namedParameterJdbcTemplate.update(sql, map);

    }

    @Override
    public void deleteAddress(Integer addressid, Integer customerId) {
        String sql = "delete from address where id=:id and customer_id=:customerid";
        Map<String, Object> map = new HashMap<>();
        map.put("id", addressid);
        map.put("customerid", customerId);
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void setDefaultAddress(Integer addressid,Integer customerId) {
        String sql = "update address set default_address=1 where id=:id  and customer_id=:customerid";
        Map<String, Object> map = new HashMap<>();
        map.put("id", addressid);
        map.put("customerid",customerId);
        namedParameterJdbcTemplate.update(sql,map);

    }

    @Override
    public void setNonDefaultForOther(Integer addressid,Integer customerId) {
        String sql = "update address set default_address=0 where id=:id and customer_id=:customerid";
        Map<String, Object> map = new HashMap<>();
        map.put("customerid",customerId);
        map.put("id",addressid);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public  List<Address> findefaultAddressById(Integer customerid) {
        String sql = "select * from address where  default_address =1 and customer_id=:customerid";
        Map<String, Object> map = new HashMap<>();
        map.put("customerid",customerid);
        List<Address>list=namedParameterJdbcTemplate.query(sql,map,new AddressMapper());
        if(list.size()>0){
            return list;
        }
        return null;
    }

    @Override
    public void setDefaultAddress4Primary(Integer addressid, Integer customerId) {
        String sql = "update address set default_address=0 where customer_id=:customerid";
        Map<String, Object> map = new HashMap<>();
        map.put("customerid",customerId);
       namedParameterJdbcTemplate.update(sql,map);

    }
}