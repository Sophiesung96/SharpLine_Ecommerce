package com.example.demo01.src.Mapper;

import com.example.demo01.src.Pojo.Role;
import com.example.demo01.src.Pojo.Users;
import com.example.demo01.src.Pojo.UsersRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersRoleMapper implements RowMapper<UsersRole> {
    @Override
    public UsersRole mapRow(ResultSet resultSet, int i) throws SQLException {
        UsersRole usersRole = new UsersRole();
        usersRole.setName(resultSet.getString("description"));
        return usersRole;
    }
}
