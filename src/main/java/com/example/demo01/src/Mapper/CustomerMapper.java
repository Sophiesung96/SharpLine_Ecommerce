package com.example.demo01.src.Mapper;

import com.example.demo01.src.Pojo.AuthenticationType;
import com.example.demo01.src.Pojo.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
        Customer customer=new Customer();
        customer.setId(resultSet.getInt("id"));
        customer.setPassword(resultSet.getString("password"));
        customer.setCreatedTime(resultSet.getTimestamp("created_time"));
        customer.setEnabled(resultSet.getInt("enabled"));
       // customer.setUpdatedTime(resultSet.getTimestamp("updated_time"));
        customer.setAddressline1(resultSet.getString("address_line1"));
        customer.setAddressline2(resultSet.getString("address_line2"));
        customer.setCity(resultSet.getString("city"));
        customer.setCountryId(resultSet.getInt("country_id"));
        customer.setEmail(resultSet.getString("email"));
        customer.setFirstName(resultSet.getString("first_name"));
        customer.setLastName(resultSet.getString("last_name"));
        customer.setPhoneNumber(resultSet.getString("phone_number"));
        customer.setPostalCode(resultSet.getString("postal_code"));
        customer.setState(resultSet.getString("state"));
        customer.setVerificationCode(resultSet.getString("verification_code"));
        customer.setAuthenticationType(resultSet.getString("Authenticationtype"));
        customer.setResetPasswordToken(resultSet.getString("reset_password_token"));
        return customer;
    }
}
