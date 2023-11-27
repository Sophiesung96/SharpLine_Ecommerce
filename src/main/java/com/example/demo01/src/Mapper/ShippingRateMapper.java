package com.example.demo01.src.Mapper;

import com.example.demo01.src.Pojo.ShippingRate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShippingRateMapper implements RowMapper<ShippingRate> {

    @Override
    public ShippingRate mapRow(ResultSet resultSet, int i) throws SQLException {
        ShippingRate shippingRate=new ShippingRate();
        shippingRate.setId(resultSet.getInt("id"));
        shippingRate.setCountry_id(resultSet.getInt("country_id"));
        shippingRate.setDays(resultSet.getInt("days"));
        shippingRate.setState(resultSet.getString("state"));
        shippingRate.setRate(resultSet.getFloat("rate"));
        shippingRate.setCodSupported(resultSet.getInt("COD_SUPPORTED"));

        return shippingRate;
    }
}
