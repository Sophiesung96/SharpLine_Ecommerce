package com.example.demo01.src.Mapper;

import com.example.demo01.src.Pojo.Currency;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyMapper implements RowMapper<Currency> {
    @Override
    public Currency mapRow(ResultSet resultSet, int i) throws SQLException {
        Currency currency=new Currency();
        currency.setId(resultSet.getInt("id"));
        currency.setCode(resultSet.getString("code"));
        currency.setSymbol(resultSet.getString("symbol"));
        currency.setName(resultSet.getString("name"));
        return currency;
    }
}
