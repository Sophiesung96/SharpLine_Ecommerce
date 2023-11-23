package com.example.demo01.src.Mapper;

import com.example.demo01.src.Pojo.Address;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressMapper implements RowMapper<Address> {
    @Override
    public Address mapRow(ResultSet resultSet, int i) throws SQLException {
        Address address=new Address();
        address.setId(resultSet.getInt("id"));
        address.setCountryId(resultSet.getInt("country_id"));
        address.setCustomerId(resultSet.getInt("customer_id"));
        address.setAddressline1(resultSet.getString("address_line1"));
        address.setAddressline2(resultSet.getString("address_line2"));
        address.setCity(resultSet.getString("city"));
        address.setState(resultSet.getString("state"));
        address.setFirstName(resultSet.getString("first_name"));
        address.setLastName(resultSet.getString("last_name"));
        address.setPhoneNumber(resultSet.getString("phone_number"));
        address.setPostalCode(resultSet.getString("postal_code"));
        address.setDefaultAddress(resultSet.getInt("default_address"));
        return address;
    }
}
