package com.example.demo01.src.Mapper;

import com.example.springboot_ecommerce.Pojo.Role;
import com.example.springboot_ecommerce.Pojo.Users;
import com.example.springboot_ecommerce.Pojo.UsersRole;
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
