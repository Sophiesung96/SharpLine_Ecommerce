package com.example.demo01.src.Mapper;

import com.example.demo01.src.Pojo.Users;
import com.example.demo01.src.Pojo.UsersRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<Users> {
    @Override
    public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
        Users users = new Users();
        users.setEmail(rs.getString("email"));
        users.setEnabled(rs.getInt("enabled"));
        users.setId(rs.getInt("id"));
        users.setFirst_name(rs.getString("first_name"));
        users.setLast_name(rs.getString("last_name"));
        users.setPassword(rs.getString("password"));
        users.setPhoto(rs.getString("photo"));
        return users;
    }
}
