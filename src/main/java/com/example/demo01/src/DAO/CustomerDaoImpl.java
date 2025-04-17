package com.example.demo01.src.DAO;

import com.example.demo01.src.Mapper.CustomerByCountryMapper;
import com.example.demo01.src.Mapper.CustomerMapper;
import com.example.demo01.src.Pojo.AuthenticationType;
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

@Repository
public class CustomerDaoImpl implements CustomerDao{

   @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public Customer findByEmail(String email) {
        String sql="select * from customers where email=:email";
        Map<String,Object> map=new HashMap<>();
        map.put("email",email);
        List<Customer>list=new ArrayList<>();
        list=namedParameterJdbcTemplate.query(sql,map,new CustomerMapper());
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public Customer findByVerificationCode(String verificationCode) {
        String sql="select * from customers where verification_code=:verification_code";
        Map<String,Object> map=new HashMap<>();
        List<Customer>list=new ArrayList<>();
        map.put("verification_code",verificationCode);
        list=namedParameterJdbcTemplate.query(sql,map,new CustomerMapper());
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public void enabled(int id) {
        String sql="update customers set enabled=:enabled , verification_code=:v where id=:id";
        Map<String,Object> map=new HashMap<>();
        List<Customer>list=new ArrayList<>();
        map.put("enabled",1);
        map.put("id",id);
        map.put("v",null);
        namedParameterJdbcTemplate.update(sql,map);

    }


    @Override
    public void registerCustomer(Customer customer) {
        String sql="insert into customers (email,password,first_name,last_name,phone_number,address_line1" +
                ",address_line2,city,state,country_id,postal_code,created_time,enabled,verification_code) values(:email,:password,:first_name,:last_name,:phone_number,:address_line1,:address_line2,:city,:state,:country_id,:postal_code,:created_time,:enabled,:verification_code)";
        Map<String,Object> map=new HashMap<>();
        map.put("email",customer.getEmail());
        map.put("password",customer.getPassword());
        map.put("first_name",customer.getFirstName());
        map.put("last_name",customer.getLastName());
        map.put("phone_number",customer.getPhoneNumber());
        map.put("address_line1",customer.getAddressline1());
        map.put("address_line2",customer.getAddressline2());
        map.put("city",customer.getCity());
        map.put("state",customer.getState());
        map.put("country_id",customer.getCountryId());
        map.put("postal_code",customer.getPostalCode());
        map.put("created_time",customer.getCreatedTime());
        map.put("enabled",customer.getEnabled());
        map.put("verification_code",customer.getVerificationCode());
        KeyHolder keyHolder=new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);

    }


    @Override
    public List<Customer> checkEmailUnique(String email) {
        String sql="select * from customers where email=:email";
        Map<String,Object> map=new HashMap<>();
        List<Customer>list=new ArrayList<>();
        map.put("email",email);
        list=namedParameterJdbcTemplate.query(sql,map,new CustomerMapper());
        if(list.size()>0){
            return list;

        }
        return null;
    }


    @Override
    public List<Customer> listAllCustomer() {
        String sql="select * from customers";
        Map<String,Object> map=new HashMap<>();
        List<Customer>list=new ArrayList<>();
        list=namedParameterJdbcTemplate.query(sql,map,new CustomerMapper());
        if(list.size()>0){
            return list;
        }
        return null;
    }


    @Override
    public List<Customer> getCategoryForCustomers() {
        String sql="select countries.name as countryName from customers inner join countries where customers.country_id=countries.id";
        Map<String,Object> map=new HashMap<>();
        List<Customer>list=new ArrayList<>();
        list=namedParameterJdbcTemplate.query(sql,map,new CustomerByCountryMapper());
        if(list.size()>0){
            return list;
        }
        return null;
    }

    @Override
    public void updateEnabledById(int enabled, int id) {
        String sql="update customers set enabled=:enabled where id=:id";
        Map<String,Object> map=new HashMap<>();
        map.put("enabled",enabled);
        map.put("id",id);
        namedParameterJdbcTemplate.update(sql,map);
    }



    @Override
    public Customer EditCustomerById(int id) {
        String sql="select * from customers where id=:id";
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        List<Customer>list=new ArrayList<>();
        list=namedParameterJdbcTemplate.query(sql,map,new CustomerMapper());
        if(list.size()>0){
            return list.get(0);

        }        return null;
    }

    @Override
    public void updateCustomer(Customer customer) {
        String sql="update customers set email=:email,password=:password,first_name=:first_name,last_name=:last_name,phone_number=:phone_number,address_line1=:address_line1" +
                ",address_line2=:address_line2,city=:city,state=:state,country_id=:country_id,postal_code=:postal_code,created_time=:created_time,enabled=:enabled,verification_code=:verification_code where id=:id";
        Map<String,Object> map=new HashMap<>();
        map.put("id",customer.getId());
        map.put("email",customer.getEmail());
        map.put("password",customer.getPassword());
        map.put("first_name",customer.getFirstName());
        map.put("last_name",customer.getLastName());
        map.put("phone_number",customer.getPhoneNumber());
        map.put("address_line1",customer.getAddressline1());
        map.put("address_line2",customer.getAddressline2());
        map.put("city",customer.getCity());
        map.put("state",customer.getState());
        map.put("country_id",customer.getCountryId());
        map.put("postal_code",customer.getPostalCode());
        map.put("created_time",customer.getCreatedTime());
        map.put("enabled",customer.getEnabled());
        map.put("verification_code",customer.getVerificationCode());
        namedParameterJdbcTemplate.update(sql,map);

    }

    @Override
    public void deleteCustomerById(int id) {
        String sql="delete from customers where id=:id";
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        namedParameterJdbcTemplate.update(sql,map);
    }


    @Override
    public Customer findCustomerById(int id) {
        String sql="select * from customers  where id=:id";
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        List<Customer>list=new ArrayList<>();
        list=namedParameterJdbcTemplate.query(sql,map,new CustomerMapper());
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public Customer findCountryPerCustomerById(int id) {
        String sql="select countries.name as countryName, countries.code as countryCode  from  customers inner join countries on customers.country_id=countries.id where customers.id=:id";
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        List<Customer>list=new ArrayList<>();
        list=namedParameterJdbcTemplate.query(sql,map,new CustomerByCountryMapper());
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public void updateAuthenticationType(int id,AuthenticationType authenticationType) {
        String sql="update customers set authenticationType=:auth where id=:id";
        Map<String,Object> map=new HashMap<>();
        map.put("auth",authenticationType.name());
        map.put("id",id);
        namedParameterJdbcTemplate.update(sql,map);

    }

    @Override
    public Customer getCustomerByEmail(String email) {
        String sql="select * from customers where email=:email";
        Map<String,Object> map=new HashMap<>();
        map.put("email",email);
        List<Customer>list=namedParameterJdbcTemplate.query(sql,map,new CustomerMapper());
        if(list.size()>0){
            return list.get(0);

        }        return null;
    }



    @Override
    public Customer findByName(String name) {
        String sql="select * from customers where first_name=:name";
        Map<String,Object> map=new HashMap<>();
        map.put("name",name);
        List<Customer>list=namedParameterJdbcTemplate.query(sql,map,new CustomerMapper());

        if(list.size()>0){
            return list.get(0);

        }        return null;
    }

    @Override
    public void updateResetPasswordToken(String email,String resetToken) {
        String sql="update  customers set reset_password_token=:token where email=:email";
        Map<String,Object> map=new HashMap<>();
        map.put("token",resetToken);
        map.put("email",email);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public Customer findByResetPasswordToken(String resetPasswordToken) {
        String sql="select * from customers where reset_password_token=:token";
        Map<String,Object> map=new HashMap<>();
        map.put("token",resetPasswordToken);

        List<Customer>list=namedParameterJdbcTemplate.query(sql,map,new CustomerMapper());
        if(list.size()>0){
            return list.get(0);

        }        return null;
    }

    @Override
    public void updateCustomerNewPasswordByToken(String token, String newPassword) {
        String sql="update customers set password=:password where reset_password_token=:token";
        Map<String,Object> map=new HashMap<>();
        map.put("token",token);
        map.put("password",newPassword);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public Customer getCustomerByfullName(String fullName) {
        String sql="SELECT * FROM customers WHERE first_name=:firstName and last_name=:lastName";
        Map<String,Object> map=new HashMap<>();
        String name[]=fullName.split(" ");
        map.put("firstName",name[0]);
        map.put("lastName",name[1]);
        List<Customer> list=namedParameterJdbcTemplate.query(sql,map,new CustomerMapper());
        if(list.size()>0){
            return list.get(0);

        }
        return null;
    }

    @Override
    public void encodePasswordByCustomerId(int customerId, String encodedPassword) {
        String sql="update customers set password=:password where id=:customerid";
        Map<String,Object>map=new HashMap<>();
        map.put("password",encodedPassword);
        map.put("customerid",customerId);
        namedParameterJdbcTemplate.update(sql,map);
    }
}
