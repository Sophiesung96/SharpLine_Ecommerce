package com.example.demo01.src.Mapper;

import com.example.springboot_ecommerce.Pojo.PageNumber;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PageNumberMapper implements RowMapper<PageNumber> {
    @Override
    public PageNumber mapRow(ResultSet resultSet, int i) throws SQLException {
        PageNumber page = new PageNumber();
        page.setPagenumber(resultSet.getInt("total"));
        return page;
    }
}
