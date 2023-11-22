package com.example.demo01.src.Mapper;

import com.example.springboot_ecommerce.Pojo.Country;
import com.example.springboot_ecommerce.Pojo.State;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StateMapper implements RowMapper<State> {
    @Override
    public State mapRow(ResultSet resultSet, int i) throws SQLException {
        State state=new State();
        state.setId(resultSet.getInt("id"));
        state.setName(resultSet.getString("name"));
        state.setCountryId(resultSet.getInt("country_id"));
        return state;
    }
}
