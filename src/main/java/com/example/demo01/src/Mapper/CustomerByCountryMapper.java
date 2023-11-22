package com.example.demo01.src.Mapper;

import com.example.springboot_ecommerce.Pojo.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomerByCountryMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
        Customer customer=new Customer();
        customer.setCountryName(resultSet.getString("countryName"));
        return customer;
    }
}
